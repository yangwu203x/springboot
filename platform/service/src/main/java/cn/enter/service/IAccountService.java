package cn.enter.service;

import cn.enter.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 16:42
 */
public interface IAccountService {
    Account findAccountByUsername(String username);

    //发送验证码
    void captcha(HttpServletResponse response,HttpServletRequest request) throws IOException;

    Page<Account> findAll(Pageable pageable);
}
