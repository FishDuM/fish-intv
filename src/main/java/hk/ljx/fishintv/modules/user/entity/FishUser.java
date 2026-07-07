package hk.ljx.fishintv.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Fish
 * @since 2026-07-08
 */
@Getter
@Setter
@ToString
@TableName("fish_user")
@Schema(name = "FishUser", description = "")
public class FishUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("is_admin")
    private Integer isAdmin;

    @TableField("is_vip")
    private Integer isVip;
}
