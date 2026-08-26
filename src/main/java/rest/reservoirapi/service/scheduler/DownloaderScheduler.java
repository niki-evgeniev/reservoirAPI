package rest.reservoirapi.service.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.DownloadFileService;
import rest.reservoirapi.service.PdfReaderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DownloaderScheduler {

    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter LOG_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final DownloadFileService downloadFileService;
    private final PdfReaderService pdfReaderService;
    private final Logger LOGGER = LoggerFactory.getLogger(DownloaderScheduler.class);

    public DownloaderScheduler(DownloadFileService downloadFileService, PdfReaderService pdfReaderService) {
        this.downloadFileService = downloadFileService;
        this.pdfReaderService = pdfReaderService;
    }

    @Scheduled(cron = "0 */10 11-21 * * MON-FRI", zone = "Europe/Sofia")
//    @Scheduled(cron = "0 * * * * *", zone = "Europe/Sofia")
//    @Scheduled(cron = "* * * * * *", zone = "Europe/Sofia")


    public void DownloadInformation() {
        boolean isDownloadFile = downloadFileService.checkFileIsDownload();
//        boolean isDownloadFile = false;
        if (isDownloadFile) {
            LOGGER.info("File exist");
            return;
        }

        String dateToDownload = downloadFileService.getDateToDownload();
        String docFileName = downloadFileService.downloadReservoirInfoDoc();
        if (!docFileName.equals("error")) {
            pdfReaderService.readDoc(docFileName);
            LOGGER.info("Successful DOC download - DownloaderScheduler");
            return;
        }

        LOGGER.info("DOC file is not available. Trying PDF download");
        String pdfFileName = downloadFileService.downloadReservoirInfoPdf();
        if (!pdfFileName.equals("error")) {
            pdfReaderService.readPdf(pdfFileName);
            LOGGER.info("Successful PDF download - DownloaderScheduler");
            return;
        }

        LocalDate missingDate = LocalDate.parse(dateToDownload, FILE_DATE_FORMATTER);
        LOGGER.warn("За дата {} все още няма качена информация.", missingDate.format(LOG_DATE_FORMATTER));
    }
}
