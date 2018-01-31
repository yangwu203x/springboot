package cn.enter.config.shiro;

import cn.enter.config.shiro.filter.CustomFormAuthenticationFilter;
import cn.enter.config.shiro.web.MyWebSessionManager;
import cn.enter.dao.RedisSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 * 
Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。 
既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * 
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Configuration
public class ShiroConfiguration {

	@Autowired
	RedisSessionDao sessionDao;

	/**
	 * 身份认证realm;
	 * (这个需要自己写，账号密码校验；权限等)
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
		
	
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 * 
	 	Filter Chain定义说明 
		1、一个URL可以配置多个Filter，使用逗号分隔 
		2、当设置多个过滤器时，全部验证通过，才视为通过 
		3、部分过滤器可指定参数，如perms，roles
	 * 
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

		 // 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//自定义验证码校验过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc",customFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");


		//<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	    //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/static/**","anon");
		filterChainDefinitionMap.put("/account/captcha","anon");
		filterChainDefinitionMap.put("/**","authc");


		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/account/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/account/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/404");


		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public CustomFormAuthenticationFilter customFormAuthenticationFilter(){
		CustomFormAuthenticationFilter customFormAuthenticationFilter = new CustomFormAuthenticationFilter();
		customFormAuthenticationFilter.setUsernameParam("username");
		customFormAuthenticationFilter.setPasswordParam("password");
		customFormAuthenticationFilter.setRememberMeParam("rememberMe");
		customFormAuthenticationFilter.setLoginUrl("/account/login");
		return customFormAuthenticationFilter;
	}


	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(myShiroRealm());

		//注入缓存管理器
//		securityManager.setCacheManager(ehCacheManager());

		// session管理器
		securityManager.setSessionManager(configWebSessionManager());

		//注入记住密码
		securityManager.setRememberMeManager(rememberMeManager());

		return securityManager;
	}




	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 *  所以我们需要修改下doGetAuthenticationInfo中的代码;
	 * ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

		return hashedCredentialsMatcher;
	}

	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * shiro缓存管理器
	 * 需要注入对应的其他的实体类中
	 * 1、安全管理器：securityManager
	 * 可见securityManager是整个shiro的核心；
	 * @return
	 */
//	@Bean
//	public EhCacheManager ehCacheManager(){
//		System.out.println("ShiroConfiguration.getEhCacheManager()");
//		EhCacheManager cacheManager = new EhCacheManager();
//		cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
//		return cacheManager;
//	}

	@Bean
	public SimpleCookie rememberMeCookie(){
		System.out.println("ShiroConfiguration.rememberMeCookie()");
		//这个参数是cookie的名称，对应前端的checkbox的name = remmberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//<!-- 记住我cookie生效时间 30天，单位秒； -->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager(){
		System.out.println("ShiroConfiguration.rememberMeManager");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}

	@Bean
	public MyWebSessionManager configWebSessionManager(){
		MyWebSessionManager manager = new MyWebSessionManager();
//		manager.setCacheManager(ehCacheManager());// 加入缓存管理器
		manager.setSessionDAO(sessionDao);// 设置SessionDao
		manager.setDeleteInvalidSessions(true);// 删除过期的session
		manager.setGlobalSessionTimeout(sessionDao.getExpireTime());// 设置全局session超时时间
		manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session

		return manager;
	}
}
