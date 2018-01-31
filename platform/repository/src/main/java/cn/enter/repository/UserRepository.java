package cn.enter.repository;

import cn.enter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 14:09
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByUsername(String username);
}
