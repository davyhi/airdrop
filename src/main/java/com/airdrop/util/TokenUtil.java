package com.airdrop.util;

import com.airdrop.entity.Privileges;
import com.airdrop.vo.UserVo;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * @author Jerry
 * @date 2018/7/30
 * @description JWT加密
 */
public class TokenUtil {

    // token拥有的用户信息
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String PRIVILEGES = "privileges";
    private static final String ROLE = "role";
    // token键
    public static final String TOKEN = "X-T";
    // 盐值
    private static final String YZ = "MMP";


    /**
     * 测试token加密解密
     *
     * @param args
     */
    public static void main(String[] args) {
        // 加密
        String val = "DD";
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "张三");
        map.put("sex", "男");
        String jwt = createJWT(map, val);
        System.err.println(jwt);
        // 解密
        Claims claims = parseJWT(jwt, val);
        System.err.println(claims.get("name") + "--" + claims.get("id"));

    }

    /**
     * 解密
     *
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            return Jwts.parser().setSigningKey(base64Security.getBytes()).parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 前三个参数为自己用户token的一些信息比如id，权限，名称等。
     * 不要将隐私信息放入
     *
     * @param map
     * @param base64Security
     * @return
     */
    public static String createJWT(Map<String, Object> map, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setPayload(new Gson().toJson(map))
                //估计是第三段密钥
                .signWith(signatureAlgorithm, base64Security.getBytes());
        //生成JWT
        return builder.compact();
    }


    /**
     * 根据用户信息生成token
     *
     * @param user
     * @return 返回token字符串
     */
    public static String createToken(UserVo user) {
        // 存储用户信息
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(ID, user.getId());
        userMap.put(NAME, user.getName());
        userMap.put(PHONE, user.getPhone());
        userMap.put(EMAIL, user.getEmail());
        userMap.put(ROLE, user.getRoles());
        userMap.put(PRIVILEGES, user.getPris());
        // 生成token
        return createJWT(userMap, YZ);
    }

    /**
     * 根据解密token获得用户信息
     *
     * @param token
     * @return
     */
    public static UserVo getUser(String token) {
        // 解密token，获取用户map
        Claims claims = parseJWT(token, YZ);
        // claims不为空则获取用户信息并返回，否则返回null
        if (claims != null) {
            return new UserVo(StringUtil.parseInt(claims.get(ID)),
                    StringUtil.parseStr(claims.get(NAME)),
                    StringUtil.parseStr(claims.get(EMAIL)),
                    StringUtil.parseStr(claims.get(PHONE)),
                    parsePri(claims.get(PRIVILEGES)),
                    null);
        }
        return null;
    }

    // 数组转换实体集合
    private static List<Privileges> parsePri(Object objs) {
        if (objs == null) {
            return null;
        }
        List<Privileges> pris = new ArrayList<>();
        List<Object> obj = (List<Object>) objs;
        for (int i = 0; i < obj.size(); i++) {
            Map<String, Object> priMap = (LinkedHashMap<String, Object>) obj.get(i);
            pris.add(new Privileges(StringUtil.parseStr(priMap.get("url")), StringUtil.parseStr(priMap.get("type"))));
        }
        return pris;
    }


}