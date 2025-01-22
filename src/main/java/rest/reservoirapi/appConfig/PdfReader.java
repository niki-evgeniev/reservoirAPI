package rest.reservoirapi.appConfig;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfReader {

    public void readPdf(String filepath) throws IOException {
        String filePath = "C:/Users/Nikolay/Desktop/Rest/reservoirApi/src/main/resources/static/22012025_Bulletin_Daily.pdf";
        File file = new File(filePath);
        try {
            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setStartPage(3);
            pdfTextStripper.setEndPage(4);

            String text = pdfTextStripper.getText(document);
            System.out.println(text);
            document.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
