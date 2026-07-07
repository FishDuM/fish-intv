package hk.ljx.fishintv.modules.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {

    @NotNull(message = "用户名不能为空")
    @Size(min = 4, max = 12, message = "用户名最小4位, 最大12位")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 8, max = 12, message = "密码最小8位, 最大12位")
    private String password;
}
