package rest.reservoirapi.service.impl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class PdfReaderService {

    public void readPdf(String filepath) throws IOException {

        String fileName = "C:/Users/Nikolay/Documents/GitHub/reservoirAPI/src/main/resources/static/download/" + filepath;
        File file = new File(fileName);
        if (file.exists()) {
            try {
                PDDocument document = Loader.loadPDF(file);
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                pdfTextStripper.setStartPage(3);
                pdfTextStripper.setEndPage(5);

                String text = pdfTextStripper.getText(document);
                String[] split = text.split("\\s+");
                String[] split2 = text.split("\n");
                List<String> list = Arrays.stream(split).toList();
                boolean iskar = list.contains("Искър");
                System.out.println(iskar);
                int indexStart = 164;


                for (int i = 1; i <= 20; i++) {
                    switch (split[indexStart]) {
                        case "Ежедневен":
                        case "бюлетин":
                        case "за":
                        case "състоянието":
                        case "на":
                        case "водите":
                        case "Кокаляне":
                        case "-":
                            indexStart++;
                            break;
                        case "4":
                        case "5":
                            indexStart = indexStart + 3;
                            break;
                        default:
                            System.out.println("Язовир номер " + i);
                            System.out.println("Име язовир " + split[indexStart++]);
                            if (isWord(split[indexStart])) {
                                System.out.println(indexStart);
                                System.out.println("Име язовир " + split[indexStart]);
                                indexStart++;
                            }
                            if (split[indexStart].equals("-")) {
                                indexStart++;
                            }
                            String obem = split[indexStart++];
                            System.out.println("Общ обем " + obem);
                            System.out.println("Мъртъв Санитарен обем " + split[indexStart++]);
                            System.out.println("Наличен обем");
                            System.out.println("млн.м3 " + split[indexStart++]);
                            System.out.println("% от общия обем  " + split[indexStart++]);
                            System.out.println("Наличен полезен обем");
                            System.out.println("млн.м3 " + split[indexStart++]);
                            System.out.println("% от полезния обем " + split[indexStart++]);
                            System.out.println("Ср. денонощен приток " + split[indexStart++]);
                            System.out.println("Ср. денонощен разход " + split[indexStart++]);
                            System.out.println("ТЕНДЕНЦИЯ - " + split[indexStart++]);
                            indexStart = indexStart + 2;

                    }

                    System.out.println(split.length);
                    document.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File not exist");
        }
    }

    private static boolean isWord(String word) {
        boolean matches = word.matches("[а-яА-Я]+");
        System.out.println(matches);
        return matches;
    }
}
