package com.airdrop.service;

import com.airdrop.dto.QueryDto;
import com.airdrop.entity.Log;
import com.airdrop.repository.LogRepository;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志业务
 * @date 2018/8/16 14:43
 */
@Service("logService")
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public QueryDto find(Log log, Pageable page, String token) {
        return findLy(log, token, page);
    }

    /**
     * 查询路由
     *
     * @param log
     * @param token
     */
    private QueryDto findLy(Log log, String token, Pageable pageable) {
        QueryDto dto = new QueryDto();
        //获取当前用户
        UserVo user = TokenUtil.getUser(token);
        if (user.getId() != 1) { // 普通用户查询
            if (!StringUtils.isEmpty(log.getContent())) {
                dto.setPage(logRepository.findByUseridContent(user.getId(), log.getContent(), pageable));
            } else {
                dto.setPage(logRepository.findByUserid(user.getId(), pageable));
            }
        } else { // 管理员查询
            if (!StringUtils.isEmpty(log.getContent())) {
                dto.setPage(logRepository.findByContent(log.getContent(), pageable));
            } else {
                dto.setPage(logRepository.findAllData(pageable));
            }
        }
        return dto;
    }

    /**
     * 添加日志信息
     *
     * @param id      当前登陆用户id
     * @param content 日志内容
     * @param type    日志类型
     */
    @Async("myExecutor")
    @Transactional
    public void insertLog(int id, String content, int type) {
        logRepository.insert(content, type, id);
    }

}
