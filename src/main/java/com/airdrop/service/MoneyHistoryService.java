package com.airdrop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airdrop.entity.MoneyHistory;
import com.airdrop.repository.MoneyHistoryRepository;

import java.util.List;

@Service("moneyHistoryService")
public class MoneyHistoryService {
        //实现存储用户帐户余额的功能
    @Autowired
    private MoneyHistoryRepository historyRepository;
    //把用户通过大转盘得到的钱存到数据库中
    @Transactional
    public void insertMoney(MoneyHistory history) {
        historyRepository.saveAndFlush(history);

    }

    @Transactional
    public Integer findCountByUserId(Integer userId) {
        Integer count = historyRepository.findCountByUserId(userId);
        return count;
    }

    public List findHistoryCountByUserId(Integer userId) {
        List historyCount = historyRepository.findHistoryCountByUserId(userId);
        return historyCount;
    }
}
