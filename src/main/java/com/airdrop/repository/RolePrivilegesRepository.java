package com.airdrop.repository;

import com.airdrop.entity.RolePrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户角色后端操作
 * @date 2018/6/1 16:50
 */
public interface RolePrivilegesRepository extends JpaRepository<RolePrivileges, Long>, JpaSpecificationExecutor<RolePrivileges> {

    /**
     * 删除角色权限关联信息
     *
     * @param rid
     */
    @Query(value = "delete from t_role_privileges where r_id = ?1", nativeQuery = true)
    @Modifying
    void deleteByRid(Long rid);

}
