package org.itstep.storages.controllers;

import org.itstep.storages.responces.UploadFileResponse;
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
public class UploadFileController {

    // Путь к локальной папке на сервере, где будут храниться файлы
    private static final String localStorageDir = "/home/keeper/temp/spring/pv121";

    // Папка в точке хранения
    private static final String bucketName = "avatars";

    @PostMapping
    public ResponseEntity<UploadFileResponse>
    uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("File is Empty");
        }

        // Объект результата операции
        UploadFileResponse response = new UploadFileResponse();

        // Данные результата операции
        response.setFileName(file.getOriginalFilename());
        response.setSize(file.getSize());
        response.setContentType(file.getContentType());

        try {
            // Определяем путь к папке, куда будем сохранять файл
            String uploadDir = localStorageDir + File.separator + bucketName;

            // Создаем директорию, если она не существует
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Получаем байты файла
            byte[] bytes = file.getBytes();

            // Создаем путь к файлу
            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());

            // Записываем байты файла в созданный путь
            Files.write(filePath, bytes);

            // Файл успешно загружен
            response.setFileUrl(filePath.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null; // Ошибка загрузки файла
        }

        // Выдаем результаты операции
        return ResponseEntity.ok(response);
    }
}
