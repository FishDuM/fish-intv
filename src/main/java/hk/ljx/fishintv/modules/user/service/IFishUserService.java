package hk.ljx.fishintv.modules.user.service;

import hk.ljx.fishintv.common.result.Result;
import hk.ljx.fishintv.modules.user.dto.UserLoginDTO;
import hk.ljx.fishintv.modules.user.entity.FishUser;
import com.baomidou.mybatisplus.extension.service.IService;
import hk.ljx.fishintv.modules.user.vo.UserLoginVO;
import jakarta.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fish
 * @since 2026-07-08
 */
public interface IFishUserService extends IService<FishUser> {

    Result<UserLoginVO> doLogin(@Valid UserLoginDTO userLoginDTO);
}
