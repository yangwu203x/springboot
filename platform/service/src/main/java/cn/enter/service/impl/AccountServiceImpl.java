package cn.enter.service.impl;

import cn.enter.annotation.UsePage;
import cn.enter.dao.RedisDao;
import cn.enter.entity.Account;
import cn.enter.repository.AccountRepository;
import cn.enter.service.IAccountService;
import cn.enter.util.authCode.CreateImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 16:42
 */
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RedisDao redisDao;
    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public void captcha(HttpServletResponse response,HttpServletRequest request) throws IOException {
        Random random = new Random();
        int codeType = (random.nextInt(3)+1)%3 + 1;
        CreateImageCode vCode = new CreateImageCode(146,42,4,10,codeType);
        BufferedImage image = vCode.getBuffImg();
        String validateCode = vCode.getCode();
        String sessionId = request.getSession().getId();
        //保存验证码到redis,保存一分钟
        redisDao.setKV(sessionId,validateCode);
        response.setCharacterEncoding("utf-8");
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
