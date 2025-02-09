package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.impl.DownloadFileServiceImpl;
import rest.reservoirapi.service.impl.PdfReaderServiceImpl;

@Component
public class pdfRunner implements CommandLineRunner {

    private final DownloadFileServiceImpl downloadFile;
    private final PdfReaderServiceImpl pdfReaderService;

    public pdfRunner(DownloadFileServiceImpl downloadFile, PdfReaderServiceImpl pdfReaderService) {
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
