package photonet.server.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import photonet.server.core.exception.IORestException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtils {
    private static final String FILE_TEMP_DIRECTORY = "." + File.separator + "temp";

    public static File getFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        try {
            File shapeFile = createTempFile(fileName);
            try (OutputStream os = new FileOutputStream(shapeFile)) {
                os.write(multipartFile.getBytes());
            }
            return shapeFile;
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new IORestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static File createTempFile(String fileName) throws IOException {
        makeDirIfNotExist();
        File file = new File(FILE_TEMP_DIRECTORY + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    private static void makeDirIfNotExist() {
        File theDir = new File(FILE_TEMP_DIRECTORY);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            log.debug("Deleted the file: " + file.getName());
        } else {
            log.error("Failed to delete the file." + file.getName());
        }
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static void deleteFiles(List<File> files) {
        files.forEach(FileUtils::deleteFile);
    }

    public static String readFileToString(Resource resource) {
        try {
            return readFileToString(resource.getInputStream());
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new IORestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String readFileToString(File file) {
        try {
            return readFileToString(new FileInputStream(file));
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new IORestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static String readFileToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (is; BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }

    public static String generatePath(String dir) {
        LocalDateTime dateTime = LocalDateTime.now();
        var fileName = dateTime.format(DateTimeFormatter.ofPattern("yyMMdd_HHmm_")) + UUID.randomUUID() + ".jpg";
        return String.join(File.separator, dir, fileName);
    }

}
