package com.sunbeam.tikito.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {

    @Value("${upload.path}")
    private String uploadPath;

    public String upload(MultipartFile file) throws IOException {

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadPath);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Files.copy(
                file.getInputStream(),
                path.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING
        );

        return filename;
    }
}