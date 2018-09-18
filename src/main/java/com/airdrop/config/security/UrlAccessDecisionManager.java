package com.airdrop.config.security;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.entity.Role;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {
        Map messageMap = new HashMap<String, Object>();
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                messageMap.put("code", CodeEnum.CODE_401.getCode());
                messageMap.put("message", "\"未登录\"");
                throw new AccessDeniedException(messageMap.toString());
//                throw new BadCredentialsException(messageMap.toString());
            } else if ("ROLE_NO".equals(needRole)) {
                messageMap.put("code", CodeEnum.CODE_404.getCode());
                messageMap.put("message", "\"" + CodeEnum.CODE_404.getMessage() + "\"");
                throw new BadCredentialsException(messageMap.toString());
            } else if ("ROLE_TIMEOUT".equals(needRole)) {
                messageMap.put("code", CodeEnum.CODE_400.getCode());
                messageMap.put("message", "\"下载地址已失效\"");
                throw new AccessDeniedException(messageMap.toString());
            }
            //当前用户所具有的权限
            FilterInvocation fi = (FilterInvocation) o;
            String token = fi.getRequest().getHeader(TokenUtil.TOKEN);
            if (StringUtils.isEmpty(token)) token = fi.getRequest().getParameter(TokenUtil.TOKEN);
            UserVo user = TokenUtil.getUser(token);
            for (Role role : user.getRoles()) {
                if (role.getCode().equals(needRole)) {
                    return;
                }
            }
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(needRole)) {
//                    return;
//                }
//            }
        }
        messageMap.put("code", CodeEnum.CODE_403.getCode());
        messageMap.put("message", "\"" + CodeEnum.CODE_403.getMessage() + "\"");
        throw new AccessDeniedException(messageMap.toString());
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}