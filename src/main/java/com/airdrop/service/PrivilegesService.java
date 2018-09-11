package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.QueryDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.Privileges;
import com.airdrop.repository.PrivilegesRepository;
import com.airdrop.repository.RolePrivilegesRepository;
import com.airdrop.util.LogUtil;
import com.airdrop.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 权限业务层
 * @date 2018/7/17 10:53
 */
@Service
public class PrivilegesService {

    @Autowired
    private PrivilegesRepository privilegesRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private RolePrivilegesRepository rolePrivilegesRepository;

    /**
     * 查询
     *
     * @param pageable
     * @param privileges
     * @return
     */
    public QueryDto find(Pageable pageable, Privileges privileges, Long rid) {
        QueryDto dto = new QueryDto();
        if (rid != null) {
            dto.setPage(privilegesRepository.find(pageable, rid));
        } else if (privileges.getPid() != null) {
            dto.setData(privilegesRepository.find(privileges.getPid()));
        } else if (!StringUtils.isEmpty(privileges.getName())) {
            dto.setPage(privilegesRepository.find(pageable, privileges.getName()));
        } else {
            dto.setPage(privilegesRepository.find(pageable));
        }
        return dto;
    }

    /**
     * 查询树形结构权限
     *
     * @return
     */
    public QueryDto findTree() {
        QueryDto dto = new QueryDto();
        List<Privileges> p0 = privilegesRepository.find(0L);   // 获得一级权限
        for (Privileges p : p0) {  // 查询下级权限
            List<Privileges> p2 = privilegesRepository.find(p.getId());
            for (Privileges pp : p2) {
                pp.setChildren(privilegesRepository.find(pp.getId()));
            }
            p.setChildren(p2);
        }
        dto.setData(p0);
        return dto;
    }

    /**
     * 新增/修改
     *
     * @param privileges
     * @return
     */
    public _ResultDto saveOrUpdate(Privileges privileges, String token) {
        String content;
        if (privileges.getId() == null) {
            getCode(privileges);    // Code设置
            content = "修改权限ID：" + privileges.getId() + ",修改内容：[名称：" + privileges.getName() + ",请求地址：" + privileges.getUrl() + ",请求类型：" + privileges.getType() + ",描述：" + privileges.getDescribe() + "]";
        } else {
            content = "添加了权限ID：" + privileges.getId() + "[名称：" + privileges.getName() + ",请求地址：" + privileges.getUrl() + ",请求类型：" + privileges.getType() + ",描述：" + privileges.getDescribe() + "]";
        }
        privilegesRepository.saveAndFlush(privileges);
        logService.insertLog(TokenUtil.getUser(token).getId(), content, LogUtil.SUCCESS);
        return new _ResultDto();
    }

    /**
     * 获得最大code并自增
     *
     * @param privileges
     */
    private void getCode(Privileges privileges) {
        // Code设置
        Integer code = privileges.getPid() == null ? privilegesRepository.getMaxCode() : privilegesRepository.getMaxCode(privileges.getPid());
        StringBuffer sb = new StringBuffer((Integer.valueOf(code.toString().substring(0, 2)) + 1) + "");
        if (privileges.getPid() == null) { // 重新生成Code
            for (int i = 0; i < code.toString().length() - 2; i++) {
                sb.append("0");
            }
            privileges.setCode((Integer.valueOf(sb.toString()) + 1) + "");
            privileges.setPid(privileges.getPpid());
        } else {
            privileges.setCode((code + 1) + "");
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public QueryDto del(Long id, String token) {
        QueryDto dto = new QueryDto();
        if (privilegesRepository.getById_r(id) != null) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "资源跟角色有关联，请先解除关联!");
        } else {
            //删除数据、角色权限关联
            privilegesRepository.deleteByid(id);
            rolePrivilegesRepository.deleteByRid(id);
        }
        logService.insertLog(TokenUtil.getUser(token).getId(), "删除了权限ID：" + id, LogUtil.SUCCESS);
        return dto;
    }

}