package photonet.server.domain.service.upload;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.core.exception.IORestException;

import java.io.IOException;

public interface FileService {

    String DIR = "photos";

    byte[] getBlob(String url);

    String saveFile(byte[] file) throws IOException;

    default String saveFile(MultipartFile file) {
        try {
            return saveFile(file.getBytes());
        } catch (IOException e) {
            throw new IORestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
