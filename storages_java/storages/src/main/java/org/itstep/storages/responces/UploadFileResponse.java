package org.itstep.storages.responces;

import lombok.Data;

@Data
public class UploadFileResponse {
    private String fileName;
    private String fileUrl;
    private String filePath;
    private String contentType;
    private long size;
}
