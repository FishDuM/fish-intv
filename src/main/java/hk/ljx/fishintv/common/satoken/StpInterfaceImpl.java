package hk.ljx.fishintv.common.satoken;

import cn.dev33.satoken.stp.StpInterface;
import hk.ljx.fishintv.modules.user.entity.FishUser;
import hk.ljx.fishintv.modules.user.mapper.FishUserMapper;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private FishUserMapper fishUserMapper;

    /**
     * 返回指定账号id所拥有的权限码集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        FishUser user = fishUserMapper.selectById(Long.parseLong((String) loginId));
        String isVip = user.getIsVip().equals(0) ? "NotVip" : "VIP";
        return List.of(isVip);
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return 该账号id具有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        FishUser user = fishUserMapper.selectById(Long.parseLong((String) loginId));
        String isAdmin = user.getIsAdmin().equals(0) ? "NotAdmin" : "Admin";
        return List.of(isAdmin);
    }
}
