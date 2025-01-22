package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.appConfig.PdfReader;

@Component
public class pdfRunner implements CommandLineRunner {

    private final PdfReader pdfReader;

    public pdfRunner(PdfReader pdfReader) {
        this.pdfReader = pdfReader;
    }

    @Override
    public void run(String... args) throws Exception {
        pdfReader.readPdf("file");
    }
}
