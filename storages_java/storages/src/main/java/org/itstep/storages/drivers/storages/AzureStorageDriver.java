package org.itstep.storages.drivers.storages;

import org.springframework.web.multipart.MultipartFile;

public class AzureStorageDriver implements StorageDriverInterface
{
    @Override
    public String put(String bucketName, String fileName, MultipartFile file) {
        return null;
    }
}
