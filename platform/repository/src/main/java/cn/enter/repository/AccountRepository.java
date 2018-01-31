package cn.enter.repository;

import cn.enter.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 16:43
 */
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByUsername(String username);

    Page<Account> findAll(Pageable pageable);
}
