package cn.enter.service;

import cn.enter.entity.User;
import cn.enter.util.protocol.Body;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 14:08
 */
public interface IUserService {

    User getUserByUsername(String username);

    Body login(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
