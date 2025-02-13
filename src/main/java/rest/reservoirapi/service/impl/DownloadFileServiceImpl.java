package rest.reservoirapi.service.impl;


import org.springframework.stereotype.Service;
import rest.reservoirapi.service.DownloadFileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DownloadFileServiceImpl implements DownloadFileService {

    private final TimeServiceImpl timeServiceImpl;

    public DownloadFileServiceImpl(TimeServiceImpl timeServiceImpl) {
        this.timeServiceImpl = timeServiceImpl;
    }

    @Override
    public String downloadReservoirInfo() {
        String dateNow = timeServiceImpl.getDateNow();

//        String dateNow = "11022025";
        String saveDir = "./Download/";
        String fileName = dateNow + "_bulletin.pdf";
        System.out.println(pdfUrl);
        try {
            downloadFile(pdfUrl, saveDir, fileName);
            System.out.println("Файлът е изтеглен успешно в: " + saveDir + fileName);
        } catch (IOException | InterruptedException e) {
            System.err.println("Грешка при изтегляне на PDF: " + e.getMessage());
        }
        return fileName;

    }

//    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
////        URL url = new URL(fileURL);
//        URI uri = URI.create(fileURL);
//        URL url = uri.toURL();
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setConnectTimeout(5000);
//        connection.setReadTimeout(5000);
//
//        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//            Files.createDirectories(Paths.get(saveDir));
//
//            try (InputStream inputStream = connection.getInputStream();
//                 FileOutputStream outputStream = new FileOutputStream(new File(saveDir, fileName))) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            }
//        } else {
//            throw new IOException("Сървърът върна код: " + connection.getResponseCode());
//        }
//    }

    // Testing HttpRequest
    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException, InterruptedException {
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
