package org.itstep.storages.services;

import lombok.AllArgsConstructor;
import org.itstep.storages.drivers.storages.DriverEnum;
import org.itstep.storages.drivers.storages.LocalStorageDriver;
import org.itstep.storages.drivers.storages.MinIoStorageDriver;
import org.itstep.storages.drivers.storages.StorageDriverInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class StorageService {

    // Путь к локальной папке на сервере, где будут храниться файлы
    private final String localStorageDir  = "/home/keeper/temp/spring/pv121";

    // Настройки хранилища S3
    private String minioUrl = "http://localhost:9000";
    private String minioUsername = "user";
    private String minioPassword = "password";


    // Основной драйвер для работы с файловым хранилищем
    private StorageDriverInterface driver;

    // Коллекция доступных драйверов
    private Map<DriverEnum, StorageDriverInterface> drivers;

    /**
     * Получить экземпляр драйвера для работы с ним напрямую
     * @param disk
     * @return
     */
    public StorageDriverInterface disk (DriverEnum disk) {
        return drivers.get(disk);
    }


    public StorageService() {
        System.out.println(" Created :: StorageService ");
        drivers = new HashMap<>();
        drivers.put(DriverEnum.Local, new LocalStorageDriver(localStorageDir));
        drivers.put(DriverEnum.MinIo, new MinIoStorageDriver(minioUrl, minioUsername, minioPassword));

        driver = drivers.get(DriverEnum.MinIo);
    }

    /**
     * Загрузить файл в хранилище
     * @param bucketName
     * @param file
     * @return
     */
    public String put(String bucketName, String fileName,  MultipartFile file ) {
        return driver.put(bucketName, fileName, file);
    }
}
