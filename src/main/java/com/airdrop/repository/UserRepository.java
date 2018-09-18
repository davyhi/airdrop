package com.airdrop.repository;

import com.airdrop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户dao
 * @date 2018/9/10 18:35
 */

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findOneByPhone(String phone);
    User findOneByEmail(String email);

    @Query(value = "select count(*) from t_user where phone = ?1",nativeQuery = true)
    Integer checkUserByPhone(String phone);

    @Query(value = "select count(*) from t_user where email = ?1",nativeQuery = true)
    Integer checkUserByEmail(String email);



    @Query(value = "select money from t_user where id = ?1",nativeQuery = true)
    Integer  findBalanceByUserId(Integer id);

    /**
     * 根据手机号或邮箱查询用户
     *
     * @param username
     * @return
     */
    @Query(value = "SELECT t.* FROM t_user t WHERE t.`status` = 0 AND (t.`phone`=?1 OR t.`email`=?1)",
            nativeQuery = true)
    User findByUsername(String username);



    @Modifying
    @Transactional
    @Query(value = "update t_user u SET u.money= ?2 WHERE u.id= ?1",nativeQuery = true)
    void  updateUMoney(Integer id,Integer money);

    //根据手机号查询用户的帐户余额
    @Query(value = "select money from t_user where phone = ?1", nativeQuery = true)
    Integer findSpecialBalanceByPhone(String number);
    //根据邮箱查询用户的帐户余额
    @Query(value = "select money from t_user where email = ?1", nativeQuery = true)
    Integer findSpecialBalanceByEmail(String number);


}

