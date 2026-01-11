package rest.reservoirapi.service.impl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Service;
import rest.reservoirapi.models.entity.Reservoir;
import rest.reservoirapi.models.entity.SavedFiles;
import rest.reservoirapi.repository.ReservoirRepository;
import rest.reservoirapi.repository.SavedFileRepository;
import rest.reservoirapi.service.PdfReaderService;


import java.io.FileInputStream;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PdfReaderServiceImpl implements PdfReaderService {

    private final List<String> nameOfReservoir = List.of("Искър", "Бели Искър", "Среченска бара", "Христо Смирненски",
            "Йовковци", "Тича", "Камчия", "Ясна поляна", "Асеновец", "Боровица", "Студена", "Дяково", "Порой", "Ахелой", "Панчарево",
            "Ястребино", "Кула", "Рабиша", "Огоста", "Сопот", "Горни Дъбник", "Бели Лом", "Съединение", "Георги Трайков(Цонево)"
            , "Жребчево", "Малко Шарково", "Домлян", "Пясъчник", "Тополница", "Тракиец", "Пчелина", "Александър Стамболийски",
            "Копринка", "Белмекен-Чаира", "Белмекен", "Чаира", "Голям Беглик-Широка поляна", "Широка поляна", "Беглика", "Голям Беглик",
            "Тошков Чарк", "Батак", "Доспат", "Цанков камък", "Въча", "Кричим", "Кърджали", "Студен кладенец", "Ивайловград");
    private final ReservoirRepository reservoirRepository;
    private final SavedFileRepository savedFileRepository;

    public PdfReaderServiceImpl(ReservoirRepository reservoirRepository, SavedFileRepository savedFileRepository) {
        this.reservoirRepository = reservoirRepository;
        this.savedFileRepository = savedFileRepository;
    }

    @Override
    public void readPdf(String filepath) {

        String fileName = "./Download/" + filepath;
//        String fileName = "./Download/" + "12112025_bulletin.pdf"; //only for manual read
        File file = new File(fileName);
        System.out.println(nameOfReservoir.size());
        if (file.exists()) {
            try {
                PDDocument document = Loader.loadPDF(file);
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                pdfTextStripper.setStartPage(3);
                pdfTextStripper.setEndPage(5);

                String text = pdfTextStripper.getText(document);
                String[] getAllLine = text.split("\n");
                int indexStart = 60;
                Map<String, List<Double>> reservoirInfoMap = new LinkedHashMap<>();
                Optional<SavedFiles> byFileName = savedFileRepository.findByFileName(filepath);
                SavedFiles savedFiles = new SavedFiles();

                if (byFileName.isEmpty()) {
                    savedFiles.setFileName(filepath);
                    savedFiles.setAddedDate(LocalDate.now());

                } else {
                    System.err.println("FILE IN DB EXIST");
                    return;
                }

                for (int i = 0; i <= 80; i++) {
                    String[] wordSplit = getAllLine[indexStart].split("\\s+");
                    if (wordSplit.length >= 5) {

                        int indexWordSplit = 2;
                        String reservoirName = wordSplit[indexWordSplit++];
                        if (isWord(wordSplit[indexWordSplit])) {
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit];
                            indexWordSplit++;
                        }

                        if (wordSplit[indexWordSplit].equals("***")) {
                            indexWordSplit++;
                        }

                        if (reservoirName.startsWith("Георги Трайков")) {
                            reservoirName = reservoirName + wordSplit[indexWordSplit++];
                        }

                        if (reservoirName.startsWith("Голям")) {
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit++];
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit++];
                        }
                        if (nameOfReservoir.contains(reservoirName)) {
                            Result result = getReservoirDetails(wordSplit, indexWordSplit);


                            putToReservoirInfoMap(reservoirInfoMap, reservoirName, result.totalVolume(),
                                    result.minimumFlowVolume(), result.fillPercentage(),
                                    result.availableVolume(), result.volumePercentage(),
                                    result.inflow_m3s(), result.outflow_m3s());
                        }
                    }
                    indexStart++;
                }
//                TODO CHANGE MAP WITH LIST<RESERVOIR>
                for (Map.Entry<String, List<Double>> stringListEntry : reservoirInfoMap.entrySet()) {
                    String name = stringListEntry.getKey();
                    List<Double> valueOfReservoir = stringListEntry.getValue();
                    Reservoir reservoir = new Reservoir();

                    if (valueOfReservoir.size() == 7) {
                        reservoir.setUuid(UUID.randomUUID());
                        reservoir.setName(name);
                        reservoir.setActive(true);
                        reservoir.setTotalVolume(valueOfReservoir.get(0));
                        reservoir.setMinimumFlowVolume(valueOfReservoir.get(1));
                        reservoir.setFillPercentage(valueOfReservoir.get(2));
                        reservoir.setAvailableVolume(valueOfReservoir.get(3));
                        reservoir.setVolumePercentage(valueOfReservoir.get(4));
                        reservoir.setInflow_m3s(valueOfReservoir.get(5));
                        reservoir.setOutflow_m3s(valueOfReservoir.get(6));
                        savedFiles.setSaved(true);
                        savedFileRepository.save(savedFiles);
                        reservoir.setSavedFiles(savedFiles);
                        reservoirRepository.save(reservoir);
                    }
                }

                System.out.println("SUCC SAVE TO DB " + filepath);

            } catch (IOException e) {
                System.err.println("Error loading PDF: " + e.getMessage());
            }
        } else {
            System.err.println("File not exist");
        }
    }

    @Override
    public void readDoc(String fileName) {
        String fullPath = "./Download/" + fileName;
        File file = new File(fullPath);
        System.out.println(nameOfReservoir.size());

        if (!file.exists()) {
            System.err.println("File not exist");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             HWPFDocument document = new HWPFDocument(fis);
             WordExtractor extractor = new WordExtractor(document)) {

            String[] paragraphs = extractor.getParagraphText();

            StringBuilder sb = new StringBuilder();
            for (String p : paragraphs) {
                String line = p.replace("\r", "").trim();
                if (!line.isEmpty()) {
                    sb.append(line).append("\n");
                }
            }

            String text = sb.toString();
            String[] getAllLine = text.split("\n");
            int indexStart = 76;
//            Map<String, List<Double>> reservoirInfoMap = new LinkedHashMap<>();
            Optional<SavedFiles> byFileName = savedFileRepository.findByFileName(fileName);
            SavedFiles savedFiles = new SavedFiles();

            if (byFileName.isEmpty()) {
                savedFiles.setFileName(fileName);
                savedFiles.setAddedDate(LocalDate.now());

            } else {
                System.err.println("FILE IN DB EXIST");
                return;
            }
            List<String> allText = Arrays.stream(getAllLine).toList();

            int index = 76;

            System.out.println(allText.size());
            for (int i = index; i <= allText.size() - 1; i++) {
                if (index + 8 >= allText.size()) {
                    break;
                }
                String name = allText.get(index).trim();
                if (name.startsWith("Георги Трайков")) {
                    name = "Георги Трайков(Цонево)";
                }

                if (name.contains("***")) {
                    name = name.replace("***", "").trim();
                } else if (name.contains("**")) {
                    name = name.replace("**", "").trim();
                } else if (name.contains("*")) {
                    name = name.replace("*", "").trim();
                }
                if (nameOfReservoir.contains(name)) {
                    Reservoir reservoir = new Reservoir();
                    reservoir.setUuid(UUID.randomUUID());
                    reservoir.setName(name);
                    reservoir.setActive(true);
                    index++;
                    reservoir.setTotalVolume(Double.parseDouble(allText.get(index)
                            .replace(",", ".")));
                    index++;
                    reservoir.setMinimumFlowVolume(Double.parseDouble(allText.get(index)
                            .replace(",", ".")));
                    index = index + 2;
                    reservoir.setFillPercentage(Double.parseDouble(allText.get(index)
                            .replace(",", ".")
                            .replace("%", "")));
                    index++;
                    reservoir.setAvailableVolume(Double.parseDouble(allText.get(index)
                            .replace(",", ".")
                            .replace("%", "")));
                    index++;
                    reservoir.setVolumePercentage(Double.parseDouble(allText.get(index)
                            .replace(",", ".")
                            .replace("%", "")));
                    double inflow_m3s = 0;
                    double outflow_m3s = 0;
                    try {
                        index++;
                        inflow_m3s = Double.parseDouble(allText.get(index)
                                .replace(",", ".")
                                .replace("%", ""));
                        index++;
                        outflow_m3s = Double.parseDouble(allText.get(index)
                                .replace(",", ".")
                                .replace("%", ""));
                    } catch (NumberFormatException e) {
                        System.out.println(reservoir.getName() + " : dont have information");
                    }
                    reservoir.setInflow_m3s(inflow_m3s);
                    reservoir.setOutflow_m3s(outflow_m3s);
                    savedFiles.setSaved(true);
                    savedFileRepository.save(savedFiles);
                    reservoir.setSavedFiles(savedFiles);
                    reservoirRepository.save(reservoir);
                    System.out.println("Successful add new info for reservoir " + name + " date : " + LocalDateTime.now());
                }
                index++;
            }

            System.out.println("SUCC SAVE TO DB " + fileName);

        } catch (IOException e) {
            System.err.println("Error loading DOC: " + e.getMessage());
        }
    }

    private static Result getReservoirDetails(String[] wordSplit, int indexWordSplit) {
        double totalVolume = Double.parseDouble(wordSplit[indexWordSplit++]
                .replace(",", "."));
        double minimumFlowVolume = Double.parseDouble(wordSplit[indexWordSplit++]
                .replace(",", "."));
        indexWordSplit++;
        double fillPercentage = Double.parseDouble(wordSplit[indexWordSplit++]
                .replace(",", ".")
                .replace("%", ""));
        double availableVolume = Double.parseDouble(wordSplit[indexWordSplit++]
                .replace(",", "."));
        double volumePercentage = Double.parseDouble(wordSplit[indexWordSplit++]
                .replace(",", ".")
                .replace("%", ""));
        double inflow_m3s = 0;
        double outflow_m3s = 0;
        try {
            inflow_m3s = Double.parseDouble(wordSplit[indexWordSplit++]
                    .replace(",", ".")
                    .replace("%", ""));
            outflow_m3s = Double.parseDouble(wordSplit[indexWordSplit++]
                    .replace(",", ".")
                    .replace("%", ""));
        } catch (NumberFormatException e) {
            System.out.println(wordSplit[2] + ": dont have information");
        }

        return new Result(totalVolume, minimumFlowVolume, fillPercentage, availableVolume,
                volumePercentage, inflow_m3s, outflow_m3s);
    }

    private record Result(double totalVolume, double minimumFlowVolume, double fillPercentage,
                          double availableVolume, double volumePercentage, double inflow_m3s,
                          double outflow_m3s) {
    }

    private static void putToReservoirInfoMap(Map<String, List<Double>> reservoirInfo, String reservoirName,
                                              double totalVolume, double minimumFlowVolume, double fillPercentage,
                                              double availableVolume, double volumePercentage, double inflow_m3s,
                                              double outflow_m3s) {
        reservoirInfo.putIfAbsent(reservoirName, new ArrayList<>());
        reservoirInfo.get(reservoirName).add(totalVolume);
        reservoirInfo.get(reservoirName).add(minimumFlowVolume);
        reservoirInfo.get(reservoirName).add(fillPercentage);
        reservoirInfo.get(reservoirName).add(availableVolume);
        reservoirInfo.get(reservoirName).add(volumePercentage);
        reservoirInfo.get(reservoirName).add(inflow_m3s);
        reservoirInfo.get(reservoirName).add(outflow_m3s);
    }

    private static boolean isWord(String word) {
        boolean matches = word.matches("[а-яА-Я]+");
        return matches;
    }
}
