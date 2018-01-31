package cn.enter.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/17 0017 16:27
 */
@Service
@PropertySource(value = {"classpath:config/config.properties"})
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisSessionDao extends AbstractSessionDAO{
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private long expireTime = 1200000;
    @Value("${redis.key.prefix}")
    private String redisPrefixKey;

    private String getKey(String originalKey) {
        return redisPrefixKey + ":"+originalKey;
    }

    @Autowired
    private RedisTemplate redisTemplate;//Redis操作类

    public RedisSessionDao() {
        super();
    }

    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        logger.info("===============doCreate================创建session,id=[{}]",session.getId().toString());

        redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
//        logger.info("==============doReadSession=================获取session,id=[{}]",sessionId.toString());
        Session readSession = null;
        try {
            readSession=(Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return readSession;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
//        logger.info("===============update================更新session，id=[{}]",session.getId().toString());
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        if (null == session) {
            return;
        }
//        logger.info("===============delete================删除session，id=[{}]",session.getId().toString());
        redisTemplate.opsForValue().getOperations().delete(getKey(session.getId().toString()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
//        logger.info("==============getActiveSessions=================获取存活的session");
        return redisTemplate.keys("*");
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
