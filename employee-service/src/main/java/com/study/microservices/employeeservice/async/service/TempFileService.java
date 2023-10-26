package com.study.microservices.employeeservice.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
public class TempFileService {

    /**
     * Transfers a MultipartFile to a temporary File with default extension.
     * Sets the file to be deleted upon termination of the JVM. However, the
     * file should still be deleted once it is no longer needed.
     */
    public File transferMultipartFileToTmpFile(MultipartFile multipartFile) throws IOException {
        final var file = Files.createTempFile("file-", null).toFile();
        multipartFile.transferTo(file);
        file.deleteOnExit();
        return file;
    }

    /**
     * Deletes a file if it exists. Logs an error if an exception is thrown.
     */
    public void deleteFileIfExists(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
            log.error("Failed to delete file {} with reason: {}", file.getName(), e.getMessage());
        }
    }
}
