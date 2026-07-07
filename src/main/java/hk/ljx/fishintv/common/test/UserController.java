package hk.ljx.fishintv.common.test;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import hk.ljx.fishintv.common.exception.BusinessException;
import hk.ljx.fishintv.common.result.Result;
import hk.ljx.fishintv.modules.user.dto.UserLoginDTO;
import hk.ljx.fishintv.modules.user.entity.FishUser;
import hk.ljx.fishintv.modules.user.mapper.FishUserMapper;
import hk.ljx.fishintv.modules.user.service.IFishUserService;
import hk.ljx.fishintv.modules.user.vo.UserLoginVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static hk.ljx.fishintv.common.exception.ErrorCode.LOGIN_ERROR;
import static hk.ljx.fishintv.modules.user.contents.UserConstant.ADMIN;
import static hk.ljx.fishintv.modules.user.contents.UserConstant.VIP;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IFishUserService  fishUserService;

    @PostMapping("/login")
    public Result<UserLoginVO> doLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return fishUserService.doLogin(userLoginDTO);
    }

    @GetMapping("/logout")
    public void isLogin() {
        StpUtil.logout();
    }

    @SaCheckPermission(VIP)
    @GetMapping("/test")
    public String test() {
        return "成功";
    }
    
}
