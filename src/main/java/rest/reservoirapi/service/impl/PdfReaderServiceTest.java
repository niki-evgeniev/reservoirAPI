package rest.reservoirapi.service.impl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PdfReaderServiceTest {

    private final List<String> nameOfReservoir = List.of("Искър", "Бели Искър", "Среченска бара", "Христо Смирненски",
            "Йовковци", "Тича", "Камчия", "Ясна поляна", "Асеновец", "Боровица", "Студена", "Дяково", "Порой", "Ахелой", "Панчарево",
            "Ястребино", "Кула", "Рабиша", "Огоста", "Сопот", "Горни Дъбник", "Бели Лом", "Съединение", "Георги Трайков(Цонево)"
            , "Жребчево", "Малко Шарково", "Домлян", "Пясъчник", "Тополница", "Тракиец", "Пчелина", "Александър Стамболийски",
            "Копринка", "Белмекен-Чаира", "Белмекен", "Чаира", "Голям Беглик-Широка поляна", "Широка поляна", "Беглика",
            "Тошков Чарк", "Батак", "Доспат", "Цанков камък", "Въча", "Кричим", "Кърджали", "Студен кладенец", "Ивайловград");

    public void readPdf(String filepath) throws IOException {

        String fileName = "C:/Users/Nikolay/Documents/GitHub/reservoirAPI/src/main/resources/static/download/" + filepath;
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
                int indexStart = 66;
                Map<String, List<Double>> reservoirInfoMap = new LinkedHashMap<>();


                for (int i = 0; i <= 72; i++) {
                    String[] wordSplit = getAllLine[indexStart].split("\\s+");
                    if (wordSplit.length >= 5) {
                        int indexWordSplit = 2;
                        String reservoirName = wordSplit[indexWordSplit++];
                        if (isWord(wordSplit[indexWordSplit])) {
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit];
                            indexWordSplit++;
                        }

                        if (reservoirName.startsWith("Георги Трайков")) {
                            reservoirName = reservoirName + wordSplit[indexWordSplit++];
                        }

                        if (reservoirName.startsWith("Голям")) {
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit++];
                            reservoirName = reservoirName + " " + wordSplit[indexWordSplit++];
                            System.out.println();
                        }
                        if (nameOfReservoir.contains(reservoirName)) {
                            Result result = getReservoirDetails(wordSplit, indexWordSplit);


                            putToReservoirInfoMap(reservoirInfoMap, reservoirName, result.totalVolume(),
                                    result.minimumFlowVolume(), result.fillPercentage(),
                                    result.availableVolume(), result.volumePercentage());
                        }
                    }
                    indexStart++;
                }

                for (Map.Entry<String, List<Double>> stringListEntry : reservoirInfoMap.entrySet()) {
                    String key = stringListEntry.getKey();
                    List<Double> value = stringListEntry.getValue();
                    System.out.println();
                }

            } catch (IOException e) {
                System.err.println("Error loading PDF: " + e.getMessage());
            }
        } else {
            System.err.println("File not exist");
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
        return new Result(totalVolume, minimumFlowVolume, fillPercentage, availableVolume, volumePercentage);
    }

    private record Result(double totalVolume, double minimumFlowVolume, double fillPercentage,
                          double availableVolume, double volumePercentage) {
    }

    private static void putToReservoirInfoMap(Map<String, List<Double>> reservoirInfo, String reservoirName,
                                              double totalVolume, double minimumFlowVolume, double fillPercentage,
                                              double availableVolume, double volumePercentage) {
        reservoirInfo.putIfAbsent(reservoirName, new ArrayList<>());
        reservoirInfo.get(reservoirName).add(totalVolume);
        reservoirInfo.get(reservoirName).add(minimumFlowVolume);
        reservoirInfo.get(reservoirName).add(fillPercentage);
        reservoirInfo.get(reservoirName).add(availableVolume);
        reservoirInfo.get(reservoirName).add(volumePercentage);
    }

    private static boolean isWord(String word) {
        boolean matches = word.matches("[а-яА-Я]+");
        System.out.println(matches);
        return matches;
    }
}
