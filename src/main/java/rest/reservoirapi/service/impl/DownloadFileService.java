package rest.reservoirapi.service.impl;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DownloadFileService {

    private final TimeService timeService;

    public DownloadFileService(TimeService timeService) {
        this.timeService = timeService;
    }

    public String downloadReservoirInfo() {

//        String dateNow = timeService.getDateNow();
        String dateNow = "05022025";

        String saveDir = "src/main/resources/static/download/"; // Директория за запазване на файла
        String fileName = dateNow + "_bulletin.pdf"; // Име на файла
        System.out.println(pdfUrl);
        try {
            downloadFile(pdfUrl, saveDir, fileName);
            System.out.println("Файлът е изтеглен успешно в: " + saveDir + fileName);
        } catch (IOException e) {
            System.err.println("Грешка при изтегляне на PDF: " + e.getMessage());
        }
        return fileName;
    }

    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Проверка дали връзката е успешна (HTTP 200)
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Увери се, че папката `static` съществува
            Files.createDirectories(Paths.get(saveDir));

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(new File(saveDir, fileName))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            throw new IOException("Сървърът върна код: " + connection.getResponseCode());
        }
    }
}
