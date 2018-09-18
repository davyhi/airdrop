package com.airdrop.repository;

import com.airdrop.entity.MoneyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 余额历史dao
 * @date 2018/9/11 11:08
 */

public interface MoneyHistoryRepository extends JpaRepository<MoneyHistory, Integer>, JpaSpecificationExecutor<MoneyHistory> {


    Integer  findCountByUserId(Integer userId);

    /*
    * 根据用户id查询 用户空投历史记录
    * */
    @Query(value = "select time_stamp , money from t_money_history where t_user_id= ?1",nativeQuery = true)
    List findHistoryCountByUserId(Integer userId);
}

