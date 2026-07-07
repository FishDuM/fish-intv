package hk.ljx.fishintv.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    STORAGE_UPLOAD_FAILED(5001, "文件上传失败"),
    STORAGE_DOWNLOAD_FAILED(5002, "文件下载失败"),

    LOGIN_ERROR(5101, "登录失败,账号不存在或密码错误"),
    NOT_LOGIN(5102, "请先登录"),
    NOT_VIP(5103, "无 VIP 权限"),
    NOT_ADMIN(5104, "无管理员权限"),

    ;

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
