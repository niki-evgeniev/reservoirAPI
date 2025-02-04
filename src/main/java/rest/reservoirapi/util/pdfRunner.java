package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.impl.DownloadFileService;
import rest.reservoirapi.service.impl.PdfReaderService;

@Component
public class pdfRunner implements CommandLineRunner {

    private final PdfReaderService pdfReaderService;
    private final DownloadFileService downloadFile;

    public pdfRunner(PdfReaderService pdfReaderService,  DownloadFileService downloadFile) {
        this.pdfReaderService = pdfReaderService;

        this.downloadFile = downloadFile;
    }

    @Override
    public void run(String... args) throws Exception {
        String fileName = downloadFile.downloadReservoirInfo();
        Thread.sleep(5000);
        pdfReaderService.readPdf(fileName);

    }
}
