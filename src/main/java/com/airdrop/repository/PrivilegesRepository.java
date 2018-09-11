package com.airdrop.repository;

import com.airdrop.entity.Privileges;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 权限后端操作
 * @date 2018/6/1 16:50
 */
public interface PrivilegesRepository extends JpaRepository<Privileges, Long>, JpaSpecificationExecutor<Privileges> {

    /**
     * 查询最大code
     *
     * @param pid
     * @return
     */
    @Query(value = "select max(`code`) from t_privileges where (id = ?1 or pid = ?1) and `status`=0", nativeQuery = true)
    Integer getMaxCode(Long pid);

    /**
     * 查询最大code
     *
     * @return
     */
    @Query(value = "select max(`code`) from t_privileges where `status`=0", nativeQuery = true)
    Integer getMaxCode();

    /**
     * 根据角色id获取权限
     *
     * @param roleids
     * @return
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`, '' role_codes from t_privileges t,t_role_privileges t2 where t.id = t2.p_id and t2.r_id in (:roleids) and t.`status`=0 GROUP BY t.id", nativeQuery = true)
    List<Privileges> findByROles(@Param("roleids") String roleids);

    /**
     * 根据pid查询
     *
     * @return
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`, '' role_codes from t_privileges t where t.pid = ?1 and t.`status`=0", nativeQuery = true)
    List<Privileges> find(Long pid);

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`, '' role_codes from t_privileges t where t.`status`=0", nativeQuery = true, countQuery = "select COUNT(1) from t_privileges t where t.`status`=0")
    Page<Privileges> find(Pageable pageable);

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`, '' role_codes from t_privileges t,t_role_privileges t2 where t.id = t2.p_id and t2.r_id = :rid and t.`status`=0", nativeQuery = true, countQuery = "select COUNT(0) from t_privileges t,t_role_privileges t2 where t.id = t2.p_id and t2.r_id = :rid and t.`status`=0")
    Page<Privileges> find(Pageable pageable, @Param("rid") Long rid);

    /**
     * 分页查询
     *
     * @param pageable
     * @param name
     * @return
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`, '' role_codes from t_privileges t where t.`name` like concat('%',:name,'%') and t.`status`=0", nativeQuery = true, countQuery = "select COUNT(0) from t_privileges t where t.`name` like concat('%',:name,'%') and t.`status`=0")
    Page<Privileges> find(Pageable pageable, @Param("name") String name);

    /**
     * 根据权限id查询是否有跟角色绑定
     *
     * @param id
     */
    @Query(value = "select t.id,t.`name`,t.`describe`,t.url,t.pid,t.ppid,t.`type`,t.`code`,'' role_codes from t_privileges t,t_role_privileges t2 where t.id = t2.p_id and t.id = ?1 and t.`status`=0 GROUP BY t.id", nativeQuery = true)
    Privileges getById_r(Long id);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Query(nativeQuery = true, value = "update t_privileges set `status`=1 where id = ?1")
    @Modifying
    int deleteByid(long id);

}
