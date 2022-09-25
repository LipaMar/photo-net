package photonet.server.domain.service.upload.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import photonet.server.core.utils.FileUtils;
import photonet.server.domain.service.upload.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
class LocalFileService implements FileService {

    public byte[] getBlob(String url) {
        try {
            File file = new File(url);
            return (new FileInputStream(file)).readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getUrl(String url) {
        log.info("get local url");
        return url;
    }

    public String saveFile(byte[] file) throws IOException {
        createDirIfNotExist();
        var newFile = Files.createFile(Path.of(FileUtils.generatePath(DIR)));
        Files.write(newFile, file);
        log.info("Created file: {}", newFile);
        return newFile.toString();
    }

    private void createDirIfNotExist() throws IOException {
        var dirPath = Path.of(DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        } else if (!Files.isDirectory(dirPath)) {
            throw new IOException();
        }
    }
}
