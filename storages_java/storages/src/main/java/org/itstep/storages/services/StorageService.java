package org.itstep.storages.services;

import lombok.AllArgsConstructor;
import org.itstep.storages.drivers.storages.LocalStorageDriver;
import org.itstep.storages.drivers.storages.StorageDriverInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class StorageService {

    // Путь к локальной папке на сервере, где будут храниться файлы
    private static final String localStorageDir = "/home/keeper/temp/spring/pv121";


    private StorageDriverInterface driver;
    public StorageService() {
        driver = new LocalStorageDriver(localStorageDir);
    }

    public String put(String bucketName, MultipartFile file ) {
        return driver.put(bucketName, file.getOriginalFilename(), file);
    }
}
