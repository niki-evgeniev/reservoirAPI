package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.impl.DownloadFileService;
import rest.reservoirapi.service.impl.PdfReaderService;
import rest.reservoirapi.service.impl.PdfReaderServiceTest;

@Component
public class pdfRunner implements CommandLineRunner {

    private final PdfReaderService pdfReaderService;
    private final DownloadFileService downloadFile;
    private final PdfReaderServiceTest pdfReaderServiceTest;

    public pdfRunner(PdfReaderService pdfReaderService, DownloadFileService downloadFile, PdfReaderServiceTest pdfReaderServiceTest) {
        this.pdfReaderService = pdfReaderService;

        this.downloadFile = downloadFile;
        this.pdfReaderServiceTest = pdfReaderServiceTest;
    }

    @Override
    public void run(String... args) throws Exception {
        String fileName = downloadFile.downloadReservoirInfo();
        Thread.sleep(1000);
//        pdfReaderService.readPdf(fileName);
        pdfReaderServiceTest.readPdf(fileName);

    }
}
