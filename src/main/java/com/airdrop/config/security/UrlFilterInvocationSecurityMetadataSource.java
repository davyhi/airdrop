package com.airdrop.config.security;

import com.airdrop.entity.Privileges;
import com.airdrop.util.SessionUtil;
import com.airdrop.util.StringUtil;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 拦截请求
 * @date 2018/7/19 10:01
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求对象
        FilterInvocation fi = (FilterInvocation) o;
        // 允许握手认证
        if (fi.getRequest().getMethod().equals("OPTIONS")) {
            return null;
        }
        // 获取token
        String token = fi.getRequest().getHeader(TokenUtil.TOKEN);
        // 判断token是否有效
        if (SessionUtil.checkUser(token, fi.getRequest().getSession())) {
            String rUrl = StringUtil.subLeft(fi.getRequestUrl(), 0, "?");
            // 判断当前请求是否在用户权限范围内,
            UserVo user = TokenUtil.getUser(token);
            if (checkPri(user.getPris(), rUrl, fi.getRequest().getMethod())) {
                return null;
            }
            return SecurityConfig.createList("ROLE_NO");
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    /**
     * 权限验证
     *
     * @param privilegesList 权限集合
     * @param rUrl           当前请求地址
     * @param type           当前请求类型
     * @return true：通过，否则请求资源不存在或权限不足
     */
    private boolean checkPri(List<Privileges> privilegesList, String rUrl, String type) {
        //匹配资源
        for (Privileges privileges : privilegesList) {
            if (StringUtils.isEmpty(privileges.getUrl()) || StringUtils.isEmpty(privileges.getType())) {
                continue;
            }
            if (antPathMatcher.match(privileges.getUrl(), rUrl) && privileges.getType().toUpperCase().equals(type)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
