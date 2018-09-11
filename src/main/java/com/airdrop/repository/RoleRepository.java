package com.airdrop.repository;

import com.airdrop.entity.Role;
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
 * @Description: 角色后端操作
 * @date 2018/6/1 16:50
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query(value = "select t.id,t.`name`,t.describe,t.`code` from t_role t,t_user_role t2 where t.id = t2.r_id and t2.u_id = :userid and t.`status`=0", nativeQuery = true)
    List<Role> findByUserId(@Param("userid") int userid);

    @Query(value = "select t.id,t.`name`,t.describe,t.`code` from t_role t where t.`status`=0", nativeQuery = true, countQuery = "select count(1) from t_privileges t where t.`status`=0")
    Page<Role> find(Pageable pageable);

    @Query(value = "select t.id,t.`name`,t.describe,t.`code` from t_role t where t.id <> 1 and t.`status`=0", nativeQuery = true, countQuery = "select count(1) from t_privileges t where t.id <> 1 and t.`status`=0")
    Page<Role> findNoOne(Pageable pageable);

    @Query(value = "select t.id,t.`name`,t.describe,t.`code` from t_role t,t_user_role t2 where t.id = t2.r_id and t2.u_id = :userid and t.`status`=0", nativeQuery = true, countQuery = "select count(1) from t_privileges t,t_user_role t2 where t.id = t2.r_id and t2.u_id = :userid and t.`status`=0")
    Page<Role> find(Pageable pageable, @Param("userid") int userid);

    @Query(value = "select t.id,t.`name`,t.describe,t.`code` from t_role t where t.`code` = :code and t.`status`=0", nativeQuery = true, countQuery = "select count(1) from t_privileges t where t.`code` = :code and t.`status`=0")
    Page<Role> findByCode(Pageable pageable, @Param("code") String code);

    /**
     * 根据角色id查询是否有跟用户绑定
     *
     * @param id
     */
    @Query(value = "select t.id,t.`name`,t.`code`,t.`describe` from t_role t,t_user_role t2 where t.id = t2.r_id and t.id = ?1 and t.`status`=0 GROUP BY t.id", nativeQuery = true)
    Role getById_u(Long id);

    /**
     * 删除
     *
     * @return
     */
    @Query(nativeQuery = true, value = "update t_role set `status`=1 where id = ?1")
    @Modifying
    int deleteByid(long id);
}
