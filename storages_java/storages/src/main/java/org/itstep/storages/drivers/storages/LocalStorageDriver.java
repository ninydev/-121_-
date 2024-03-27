package org.itstep.storages.drivers.storages;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
public class LocalStorageDriver implements StorageDriverInterface
{
    private final String localStorageDir;

    @Override
    public String put(String bucketName, String fileName, MultipartFile file) {
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
            Path filePath = Paths.get(uploadDir + File.separator + fileName);

            // Записываем байты файла в созданный путь
            Files.write(filePath, bytes);

            // Файл успешно загружен
            return filePath.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null; // Ошибка загрузки файла
        }

    }
}
