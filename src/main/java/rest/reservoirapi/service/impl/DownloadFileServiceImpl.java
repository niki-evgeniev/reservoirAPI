package rest.reservoirapi.service.impl;


import org.springframework.stereotype.Service;
import rest.reservoirapi.models.entity.SavedFiles;
import rest.reservoirapi.repository.SavedFileRepository;
import rest.reservoirapi.service.DownloadFileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DownloadFileServiceImpl implements DownloadFileService {

    private final TimeServiceImpl timeServiceImpl;
    private final SavedFileRepository savedFileRepository;

    public DownloadFileServiceImpl(TimeServiceImpl timeServiceImpl, SavedFileRepository savedFileRepository) {
        this.timeServiceImpl = timeServiceImpl;
        this.savedFileRepository = savedFileRepository;
    }

    @Override
    public String downloadReservoirInfo() {
        String dateNow = timeServiceImpl.getDateNow();
//        String dateNow = "18022025";
        String pdfUrl = "https://www.moew.government.bg/static/media/ups/tiny/Daily%20Bulletin/"
                + dateNow + "_Bulletin_Daily.pdf";
        String saveDir = "./Download/";
        String fileName = dateNow + "_bulletin.pdf";
        System.out.println(pdfUrl);
        try {
            downloadFile(pdfUrl, saveDir, fileName);
            System.out.println("Файлът е изтеглен успешно в: " + saveDir + fileName);
        } catch (IOException | InterruptedException e) {
//            System.err.println("Грешка при изтегляне на PDF: " + e.getMessage());
            System.err.println("Линкът не работи: " + e.getMessage());
            fileName = "error";
        }
        return fileName;
    }

    @Override
    public boolean checkFileIsDownload() {
        String dateNow = timeServiceImpl.getDateNow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate localDate = LocalDate.parse(dateNow, formatter);
        Optional<SavedFiles> isFileDownloaded = savedFileRepository.findByAddedDate(localDate);
        return isFileDownloaded.isPresent();
    }

    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException,
            InterruptedException {
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
