package com.airdrop.repository;

import com.airdrop.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志数据库交互操作
 * @date 2018/6/1 16:50
 */
public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {

    /**
     * 分页查询
     *
     * @return
     */
    @Query(value = "select t.id,t.content,t2.`name` ,t.type,t.create_stamp,t.user_id from t_log t,t_user t2 where t.user_id = t2.id order by t.id desc",
            nativeQuery = true, countQuery = "select COUNT(1) from t_log t,t_user t2 where t.user_id = t2.id")
    Page<Log> findAllData(Pageable pageable);

    /**
     * 分页查询
     *
     * @param userid 用户id
     * @return
     */
    @Query(value = "select t.id,t.content,t2.`name` ,t.type,t.create_stamp,t.user_id from t_log t,t_user t2 where t.user_id = t2.id and t.user_id = ?1 order by t.id desc",
            nativeQuery = true, countQuery = "select COUNT(1) from t_log t,t_user t2 where t.user_id = t2.id and t.user_id = ?1")
    Page<Log> findByUserid(int userid, Pageable pageable);

    /**
     * 分页查询
     *
     * @param userid 用户id
     * @return
     */
    @Query(value = "select t.id,t.content,t2.`name` ,t.type,t.create_stamp,t.user_id from t_log t,t_user t2 where t.user_id = t2.id and t.user_id = :userid and t.content like concat('%',:content,'%') order by t.id desc",
            nativeQuery = true, countQuery = "select COUNT(1) from t_log t,t_user t2 where t.user_id = t2.id and t.user_id = :userid and t.content like concat('%',:content,'%')")
    Page<Log> findByUseridContent(@Param("userid") int userid, @Param("content") String content, Pageable pageable);

    /**
     * 分页查询
     *
     * @param content 日志内容
     * @return
     */
    @Query(value = "select t.id,t.content,t2.`name` ,t.type,t.create_stamp,t.user_id from t_log t,t_user t2 where t.user_id = t2.id and t.content like concat('%',:content,'%') order by t.id desc",
            nativeQuery = true, countQuery = "select COUNT(1) from t_log t,t_user t2 where t.user_id = t2.id and t.content like concat('%',:content,'%')")
    Page<Log> findByContent(@Param("content") String content, Pageable pageable);

    @Query(value = "insert into t_log(content,`type`,user_id) values(?1,?2,?3)", nativeQuery = true)
    @Modifying
    int insert(String content, int type, int userid);

}
