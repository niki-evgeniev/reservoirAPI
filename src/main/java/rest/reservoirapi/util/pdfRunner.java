package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.impl.DownloadFileService;
import rest.reservoirapi.service.impl.PdfReaderService;

@Component
public class pdfRunner implements CommandLineRunner {

    private final DownloadFileService downloadFile;
    private final PdfReaderService pdfReaderService;

    public pdfRunner( DownloadFileService downloadFile, PdfReaderService pdfReaderService) {
        this.downloadFile = downloadFile;
        this.pdfReaderService = pdfReaderService;
    }

    @Override
    public void run(String... args) throws Exception {
        String fileName = downloadFile.downloadReservoirInfo();
        Thread.sleep(1000);
        pdfReaderService.readPdf(fileName);

    }
}
