package hk.ljx.fishintv.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    STORAGE_UPLOAD_FAILED(5001, "文件上传失败"),
    STORAGE_DOWNLOAD_FAILED(5002, "文件下载失败")


    ;

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
