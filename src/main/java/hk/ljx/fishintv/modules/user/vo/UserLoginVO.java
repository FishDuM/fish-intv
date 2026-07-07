package hk.ljx.fishintv.modules.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {

    private Long id;

    private String username;

    private Integer isAdmin;

    private Integer isVip;

}
