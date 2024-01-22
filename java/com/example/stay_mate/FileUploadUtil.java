package com.example.stay_mate;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class FileUploadUtil {

    public static void saveFile(String uploadDir,
                                String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            byte[] fileContent = Files.readAllBytes(filePath);
            String base64encodedImage = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException exception) {
            throw new IOException("Could not save file: " + fileName);
        }
    }
}
