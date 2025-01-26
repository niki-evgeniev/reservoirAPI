package rest.reservoirapi.service.impl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfReader {

    public void readPdf(String filepath) throws IOException {
        String filePath = "C:/Users/Nikolay/Desktop/Rest/reservoirApi/src/main/resources/static/22012025_Bulletin_Daily.pdf";
//        String filePath = "C:/Users/Nikolay/Documents/GitHub/reservoirAPI/src/main/resources/static/07012025_Bulletin_Daily.pdf";
//        String filePath = "C:/Users/Nikolay/Documents/GitHub/reservoirAPI/src/main/resources/static/07012025_Bulletin_Daily.pdf";
        File file = new File(filePath);
        try {
            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setStartPage(3);
            pdfTextStripper.setEndPage(5);

            String text = pdfTextStripper.getText(document);
            String[] split = text.split(" ");
            int indexStart = 194;
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatingDate = DateTimeFormatter.ofPattern("ddMMyyyy");
            String todayFormatedDate = today.format(formatingDate);
            System.out.println(todayFormatedDate);

            for (int i = 1; i <= 65; i++) {
                System.out.println("Язовир номер " + i);
                System.out.println("Име язовир " + split[indexStart++]);
                if (isWord(split[indexStart])) {
                    System.out.println(indexStart);
                    System.out.println("Име язовир " + split[indexStart]);
                    indexStart++;
                }
                System.out.println("Общ обем " + split[indexStart++]);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWord(String word) {
        boolean matches = word.matches("[а-яА-Я]+");
        System.out.println(matches);
        return matches;
    }
}
