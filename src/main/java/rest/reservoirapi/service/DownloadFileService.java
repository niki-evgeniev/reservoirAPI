package rest.reservoirapi.service;

public interface DownloadFileService {

    String downloadReservoirInfoPdf();

    boolean checkFileIsDownload();

    String downloadReservoirInfoDoc();
}
