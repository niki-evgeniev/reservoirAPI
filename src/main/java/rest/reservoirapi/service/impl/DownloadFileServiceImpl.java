package rest.reservoirapi.service.impl;


import org.springframework.stereotype.Service;
import rest.reservoirapi.repository.SavedFileRepository;
import rest.reservoirapi.service.DownloadFileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DownloadFileServiceImpl implements DownloadFileService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    private final TimeServiceImpl timeServiceImpl;
    private final SavedFileRepository savedFileRepository;

    public DownloadFileServiceImpl(TimeServiceImpl timeServiceImpl, SavedFileRepository savedFileRepository) {
        this.timeServiceImpl = timeServiceImpl;
        this.savedFileRepository = savedFileRepository;
    }

    @Override
    public String downloadReservoirInfoPdf() {
        String dateNow = getDateToDownload();
        String pdfUrl = "https://www.moew.government.bg/static/media/ups/tiny/water_bulletin/"
                + dateNow + "_Bulletin_Daily.pdf";
        String saveDir = "./Download/";
        String fileName = dateNow + "_bulletin.pdf";
        return getString(pdfUrl, saveDir, fileName);
    }

    @Override
    public String downloadReservoirInfoDoc() {
        String dateNow = getDateToDownload();
//        String pdfUrl = "https://www.moew.government.bg/static/media/ups/tiny/water_bulletin/"
//                + dateNow + "_Bulletin_Daily.doc";
        String pdfUrl = "https://www.moew.government.bg/static/media/ups/tiny/Daily%20Bulletin/"
                + dateNow + "_Bulletin_Daily.doc";
        String saveDir = "./Download/";
        String fileName = dateNow + "_bulletin.doc";
        return getString(pdfUrl, saveDir, fileName);
    }

    private String getString(String pdfUrl, String saveDir, String fileName) {
        System.out.println(pdfUrl);
        try {
            downloadFile(pdfUrl, saveDir, fileName);
            System.out.println("Файлът е изтеглен успешно в: " + saveDir + fileName);
        } catch (IOException | InterruptedException e) {
            System.err.println("Линкът не работи: " + e.getMessage());
            fileName = "error";
        }
        return fileName;
    }

    @Override
    public boolean checkFileIsDownload() {
        LocalDate dateToDownload = LocalDate.parse(getDateToDownload(), DATE_FORMATTER);
        return isFileDownloaded(dateToDownload);
    }

    @Override
    public String getDateToDownload() {
        LocalDate today = LocalDate.parse(timeServiceImpl.getDateNow(), DATE_FORMATTER);
        LocalDate latestWorkingDay = moveToPreviousWorkingDayIfNeeded(today);
        LocalDate previousWorkingDay = moveToPreviousWorkingDayIfNeeded(latestWorkingDay.minusDays(1));

        if (!isFileDownloaded(previousWorkingDay)) {
            return previousWorkingDay.format(DATE_FORMATTER);
        }

        return latestWorkingDay.format(DATE_FORMATTER);
    }

    private LocalDate moveToPreviousWorkingDayIfNeeded(LocalDate date) {
        LocalDate workingDay = date;
        while (workingDay.getDayOfWeek() == DayOfWeek.SATURDAY
                || workingDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            workingDay = workingDay.minusDays(1);
        }
        return workingDay;
    }

    private boolean isFileDownloaded(LocalDate date) {
        String dateAsText = date.format(DATE_FORMATTER);
        return savedFileRepository.findByFileName(dateAsText + "_bulletin.doc").isPresent()
                || savedFileRepository.findByFileName(dateAsText + "_bulletin.pdf").isPresent();
    }


    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException,
            InterruptedException {

        // Enable AIA fetching so Java can download missing intermediate CA certificates.
        System.setProperty("com.sun.security.enableAIAcaIssuers", "true");

        URI uri = URI.create(fileURL);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(java.time.Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            Files.createDirectories(Paths.get(saveDir));
            try (InputStream inputStream = response.body();
                 FileOutputStream outputStream = new FileOutputStream(new File(saveDir, fileName))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            throw new IOException("Сървърът върна код: " + response.statusCode());
        }
    }
}
