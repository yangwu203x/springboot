package cn.enter.service.impl;

import cn.enter.dao.RedisDao;
import cn.enter.entity.User;
import cn.enter.exception.EnterException;
import cn.enter.repository.UserRepository;
import cn.enter.service.IUserService;
import cn.enter.util.JWTUtil;
import cn.enter.util.encrypt.Function;
import cn.enter.util.enums.DataType;
import cn.enter.util.enums.RetCode;
import cn.enter.util.protocol.Body;
import cn.enter.util.protocol.BodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 14:08
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisDao redisDao;

    @Override
    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    @Override
    public Body login(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User perUser = userRepository.getUserByUsername(user.getUsername());
        if (perUser == null ){
            throw new EnterException(RetCode.USERNOTREGISTER);//用户未注册
        }
        if (!perUser.getPassword().equals(Function.EncoderByMd5(user.getPassword()))){
            throw new EnterException(RetCode.ERROR_PASSWORD);//密码错误
        }

        //登录成功
        redisDao.setUser(perUser.getUsername(),perUser);
        return BodyUtil.success(JWTUtil.sign(perUser.getUsername(),perUser.getPassword()),DataType.Object);
    }
}
