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

    //    @Scheduled(cron = "0 02 10 * * MON-FRI")
//    @Scheduled(cron = "0 0 8-19/2 * * MON-FRI")
//    @Scheduled(cron = "0 0 8-20/2 * * MON-FRI", zone = "Europe/Sofia")
    @Scheduled(cron = "0 0 11,12,13,14,15,16,17,18,19 * * MON-FRI", zone = "Europe/Sofia")
//    @Scheduled(cron = "0 * * * * *", zone = "Europe/Sofia")

    public void DownloadInformation() throws InterruptedException {

        boolean isDownloadFile = downloadFileService.checkFileIsDownload();
        if (!isDownloadFile) {
            String fileName = downloadFileService.downloadReservoirInfo();
            Thread.sleep(1000);
            if (!fileName.equals("error")) {
                pdfReaderService.readPdf(fileName);
                LOGGER.info("Successful download file - DownloaderScheduler");
            }
        } else {
            LOGGER.info("File exist");
        }
    }
}
