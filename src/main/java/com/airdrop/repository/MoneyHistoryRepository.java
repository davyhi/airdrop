package com.airdrop.repository;

import com.airdrop.entity.MoneyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 余额历史dao
 * @date 2018/9/11 11:08
 */

public interface MoneyHistoryRepository extends JpaRepository<MoneyHistory, Integer>, JpaSpecificationExecutor<MoneyHistory> {


}

