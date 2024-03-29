package org.itstep.storages.drivers.storages;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
public class MinIoStorageDriver implements StorageDriverInterface
{

    // Клиент для взаимодействия с хранилищем
    private MinioClient minioClient;

    /**
     * Выполняет подключение к хранилищу
     * @param minioUrl
     * @param minioUsername
     * @param minioPassword
     */

    public MinIoStorageDriver(String minioUrl,String minioUsername, String minioPassword ) {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();

        System.out.println(" Storage MinioClient Created ");
    }


    /**
     * Выгружает файл в хранилище
     * @param bucketName
     * @param fileName
     * @param file
     * @return
     */
    @Override
    public String put(String bucketName, String fileName, MultipartFile file) {
        try {
            // Создаем контейнер (ведро), если он не существует
            bucketExists(bucketName);

            // Получаем байты файла
            ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());

            // Тип контента
            String contentType = file.getContentType();

            // Отправляем байты файла в хранилище
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName).stream(bais, bais.available(), -1)
                            .contentType(contentType) // Устанавливаем content-type
                            .build());
            bais.close();

            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null; // Ошибка загрузки файла
        }

    }


    /**
     * Проверяем - есть ли нужное нам хранилище (контейнер, ведро) на сервере
     * Если его нет - создаем
     * Это аналог directory.mkdirs - при локальном хранилище
     * @param bucketName
     */
    private void bucketExists(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found =
                minioClient.bucketExists(
                        BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            // Make a new bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }
}
