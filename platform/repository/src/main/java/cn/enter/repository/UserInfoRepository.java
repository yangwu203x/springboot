package cn.enter.repository;

import cn.enter.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * UserInfo持久化类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
public interface UserInfoRepository extends CrudRepository<User,Long> {
	
	/**通过username查找用户信息;*/
	User findByUsername(String username);
	
}
