package com.scu.ams.basic.realm;

import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Shiro的认证
 */
@Component
public class BasicRealm extends AuthorizingRealm {
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建对象，封装当前登录用户的角色、权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 存储"角色"（应该从数据库中获取角色信息，但由于这个微服务只用于校友用户相关的功能，故简单处理，直接写alumnus角色，不在数据库中建角色表）
        info.addRole("alumnus");
        // 存储"权限"（同上）
        info.addStringPermission("alumnus:info"); // 查看个人信息的权限
        // 返回对象
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取用户输入信息
        String aluId = authenticationToken.getPrincipal().toString();
        // 2.获取数据库中用户的信息
        AlumnusBasicEntity alumnusBasicEntity = alumnusBasicService.getByAluId(aluId);
        // 3.非空判断，将数据封装返回
        if(alumnusBasicEntity != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    alumnusBasicEntity.getPassword(),
                    ByteSource.Util.bytes("scusalt"), // 盐暂固定为"scusalt"
                    aluId
            );
            return info;
        }
        return null;
    }
}
