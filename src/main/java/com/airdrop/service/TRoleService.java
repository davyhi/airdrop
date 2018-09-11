package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.entity.Role;
import com.airdrop.entity.RolePrivileges;
import com.airdrop.repository.RolePrivilegesRepository;
import com.airdrop.repository.RoleRepository;
import com.airdrop.util.LogUtil;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 角色业务层
 * @date 2018/7/17 10:53
 */
@Service
public class TRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private RolePrivilegesRepository rolePrivilegesRepository;

    /**
     * 查询
     *
     * @param pageable
     * @param role
     * @param userid
     * @return
     */
    public QueryDto find(Pageable pageable, Role role, Integer userid, String token) {
        QueryDto dto = new QueryDto();
        if (userid != null) {
            dto.setPage(roleRepository.find(pageable, userid));
        } else if (!StringUtils.isEmpty(role.getCode())) {
            dto.setPage(roleRepository.findByCode(pageable, role.getCode()));
        } else {
            UserVo user = TokenUtil.getUser(token);
            if (user.getId() != 1) {
                dto.setPage(roleRepository.findNoOne(pageable));
            } else {
                dto.setPage(roleRepository.find(pageable));
            }
        }
        return dto;
    }

    /**
     * 新增/修改
     *
     * @param role
     * @return
     */
    public QueryDto saveOrUpdate(Role role, String token) {
        // 保存数据
        String content;
        if (role.getId() == null) {
            content = "添加了角色ID：" + role.getId() + "，角色名:" + role.getName();
        } else {
            content = "修改了角色ID：" + role.getId() + "，修改内容:[ 名称:" + role.getName() + ",描述:" + role.getDescribe() + "]";
        }
        roleRepository.saveAndFlush(role);
        logService.insertLog(TokenUtil.getUser(token).getId(), content, LogUtil.SUCCESS);
        return new QueryDto();
    }

    /**
     * 角色授权
     *
     * @param rid
     * @param priid
     * @return
     */
    @Transactional
    public QueryDto roleAuthorization(Long rid, ArrayList<Integer> priid, String token) {
        QueryDto dto = new QueryDto();
        // 权限更新
        rolePrivilegesRepository.deleteByRid(rid);
        for (Integer pid : priid) {
            rolePrivilegesRepository.saveAndFlush(new RolePrivileges(rid, Long.valueOf(pid.toString())));
        }
        logService.insertLog(TokenUtil.getUser(token).getId(), "授予了角色ID：" + rid + "，权限ID：" + priid.toString(), LogUtil.SUCCESS);
        return dto;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public QueryDto del(long id, String token) {
        QueryDto dto = new QueryDto();
        //查看角色是否跟用户绑定
        if (roleRepository.getById_u(id) != null) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "角色跟用户有关联，请先解除关联!");
        } else {
            // 删除数据
            roleRepository.deleteByid(id);
            // 删除用户角色、角色权限关联信息
            rolePrivilegesRepository.deleteByRid(id);
        }
        logService.insertLog(TokenUtil.getUser(token).getId(), "删除角色ID：" + id, LogUtil.SUCCESS);
        return dto;
    }

}