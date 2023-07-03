package com.scu.ams.basic.config;

import com.scu.ams.basic.realm.BasicRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    @Autowired
    private BasicRealm basicRealm;

    // 配置SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        // 1.创建 defaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 2.创建加密对象，设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //      2.1采用md5加密
        matcher.setHashAlgorithmName("md5");
        //      2.2迭代加密次数
        matcher.setHashIterations(3);
        // 3.将加密对象存入到 basicRealm 中
        basicRealm.setCredentialsMatcher(matcher);
        // 4.将 basicRealm 存入到 defaultWebSecurityManager 中
        defaultWebSecurityManager.setRealm(basicRealm);
        //      4.5可选：设置rememberMe
        //defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 5.返回 defaultWebSecurityManager
        return defaultWebSecurityManager;
    }

    // Cookie属性设置
//    public SimpleCookie rememberMeCookie() {
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        // 设置跨域
//        //cookie.setDomain(domain);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(7 * 24 * 60 * 60); // cookie有效期为7天
//        return cookie;
//    }
//
//    // 创建Shiro的Cookie管理对象
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes()); // 注意需要是16位
//        return cookieRememberMeManager;
//    }

    // 配置Shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        // 创建 defaultShiroFilterChainDefinition 对象
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 设置不认证就可以访问的资源（登录）
        definition.addPathDefinition("/basic/alumnusbasic/login", "anon");
        //        // 设置登出过滤器，现已被controller中的logout接口替代
        //definition.addPathDefinition("/basic/alumnusbasic/logout-shiro", "logout");
        definition.addPathDefinition("/**", "anon");
        // 设置需要登录认证才可以访问的资源
        //definition.addPathDefinition("/**", "authc");

        // 可选：设置存在用户的过滤器(rememberMe)
        //definition.addPathDefinition("/**", "user");
        // 可选：缓存管理器，ehcache

        // 返回 definition
        return definition;
    }
}
