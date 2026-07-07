package hk.ljx.fishintv.modules.user.service.impl;

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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hk.ljx.fishintv.modules.user.vo.UserLoginVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static hk.ljx.fishintv.common.exception.ErrorCode.LOGIN_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fish
 * @since 2026-07-08
 */
@Service
public class FishUserServiceImpl extends ServiceImpl<FishUserMapper, FishUser> implements IFishUserService {

    @Resource
    private FishUserMapper fishUserMapper;


    @Override
    public Result<UserLoginVO> doLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        FishUser user = fishUserMapper.selectOne(new LambdaQueryWrapper<FishUser>().eq(FishUser::getUsername, username));
        if (ObjUtil.isNull(user) || StrUtil.hasBlank(user.getPassword(), user.getUsername())) {
            throw new BusinessException(LOGIN_ERROR);
        }
        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            throw new BusinessException(LOGIN_ERROR);
        }
        StpUtil.login(user.getId());

        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtil.copyProperties(user, userLoginVO);
        return Result.success(userLoginVO);
    }
}
