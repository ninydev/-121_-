package org.itstep.storages.drivers.storages;

import org.springframework.web.multipart.MultipartFile;

public interface StorageDriverInterface {


    /**
     * Загружает файл из контроллера в хранилище
     * @param bucketName основной контейнер
     * @param fileName путь внутри контейнера
     * @param file файл
     * @return путь внутри хранилища
     */
    public String put (String bucketName, String fileName, MultipartFile file);

}
