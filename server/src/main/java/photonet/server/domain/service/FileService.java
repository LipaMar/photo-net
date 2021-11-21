package photonet.server.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.core.exception.IORestException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    private final String DIR = "photos";

    public byte[] getBlob(String url) {
        try {
            File file = new File(this.getClass().getClassLoader().getResource(url).getFile());
            return (new FileInputStream(file)).readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String saveFile(byte[] file) throws IOException {
        createDirIfNotExist();
        var newFile = Files.createFile(Path.of(generatePath()));
        Files.write(newFile, file);
        log.info("Created file: {}", newFile);
        return newFile.toString();
    }

    public String saveFile(MultipartFile file) {
        try {
            return saveFile(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IORestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createDirIfNotExist() throws IOException {
        var dirPath = Path.of(DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        } else if (!Files.isDirectory(dirPath)) {
            throw new IOException();
        }
    }

    private String generatePath() {
        LocalDateTime dateTime = LocalDateTime.now();
        var fileName = dateTime.format(DateTimeFormatter.ofPattern("yyMMdd_HHmm_")) + UUID.randomUUID() + ".jpg";
        return String.join(File.separator, DIR, fileName);
    }
    
}
