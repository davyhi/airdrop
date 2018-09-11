package com.airdrop.repository;

import com.airdrop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户dao
 * @date 2018/9/10 18:35
 */

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 根据手机号或邮箱查询用户
     *
     * @param username
     * @return
     */
    @Query(value = "SELECT t.* FROM t_user t WHERE t.`status` = 0 AND (t.`phone`=?1 OR t.`email`=?1)",
            nativeQuery = true)
    User findByUsername(String username);
}

