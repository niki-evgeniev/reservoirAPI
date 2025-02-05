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
                String[] split = text.split("\n");
                int indexStart = 66;
                Map<String, List<Double>> reservoirInfo = new TreeMap<>();


                for (int i = 0; i <= 2 ; i++) {
                    String[] wordSplit = split[indexStart].split("\\s+");
                   reservoirInfo.putIfAbsent("Иваножо", new ArrayList<>());
                    reservoirInfo.get("Иваножо").add(15.2);
                    System.out.println(Arrays.toString(wordSplit));
                    indexStart++;
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
