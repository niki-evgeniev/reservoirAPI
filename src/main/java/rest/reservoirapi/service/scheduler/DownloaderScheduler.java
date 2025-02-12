package rest.reservoirapi.service.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.DownloadFileService;
import rest.reservoirapi.service.PdfReaderService;

@Component
public class DownloaderScheduler {

    private final DownloadFileService downloadFileService;
    private final PdfReaderService pdfReaderService;
    private final Logger LOGGER = LoggerFactory.getLogger(DownloaderScheduler.class);

    public DownloaderScheduler(DownloadFileService downloadFileService, PdfReaderService pdfReaderService) {
        this.downloadFileService = downloadFileService;
        this.pdfReaderService = pdfReaderService;
    }

    @Scheduled(cron = "0 0 12 * * MON-FRI")
    public void DownloadInformation() throws InterruptedException {
        String fileName = downloadFileService.downloadReservoirInfo();
        Thread.sleep(1000);
        pdfReaderService.readPdf(fileName);
        LOGGER.info("Successful download file - DownloaderScheduler");
    }
}
