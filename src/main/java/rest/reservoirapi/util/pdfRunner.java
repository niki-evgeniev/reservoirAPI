package rest.reservoirapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.TimeService;
import rest.reservoirapi.service.impl.DownloadFileServiceImpl;
import rest.reservoirapi.service.impl.PdfReaderServiceImpl;

@Component
public class pdfRunner implements CommandLineRunner {

    private final DownloadFileServiceImpl downloadFile;
    private final PdfReaderServiceImpl pdfReaderService;
    private final TimeService timeService;

    public pdfRunner(DownloadFileServiceImpl downloadFile, PdfReaderServiceImpl pdfReaderService, TimeService timeService) {
        this.downloadFile = downloadFile;
        this.pdfReaderService = pdfReaderService;
        this.timeService = timeService;
    }

    @Override
    public void run(String... args) throws Exception {
        String fileName = downloadFile.downloadReservoirInfo();
        String dateNow = timeService.getDateNow();
        Thread.sleep(1000);
        pdfReaderService.readPdf(fileName);

    }
}
