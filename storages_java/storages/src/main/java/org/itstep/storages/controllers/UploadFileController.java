package org.itstep.storages.controllers;

import lombok.AllArgsConstructor;
import org.itstep.storages.drivers.storages.DriverEnum;
import org.itstep.storages.responces.UploadFileResponse;
import org.itstep.storages.services.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/files")
@AllArgsConstructor
public class UploadFileController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<UploadFileResponse>
    uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        storageService
                .disk(DriverEnum.Local)
                .put("avatars", "blonda.jpg", file);

        if (file.isEmpty()) {
            throw new Exception("File is Empty");
        }

        // Объект результата операции
        UploadFileResponse response = new UploadFileResponse();

        // Данные результата операции
        response.setFileName(file.getOriginalFilename());
        response.setSize(file.getSize());
        response.setContentType(file.getContentType());

        response.setFilePath(storageService.put("avatars", file.getOriginalFilename(), file));

        // Выдаем результаты операции
        return ResponseEntity.ok(response);
    }
}
