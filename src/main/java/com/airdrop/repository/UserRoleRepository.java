package com.airdrop.repository;

import com.airdrop.entity.UserRole;
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
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    /**
     * 删除用户角色关联信息
     *
     * @param userid
     */
    @Query(value = "delete from t_user_role where u_id = ?1", nativeQuery = true)
    @Modifying
    void deleteByUid(int userid);

}
