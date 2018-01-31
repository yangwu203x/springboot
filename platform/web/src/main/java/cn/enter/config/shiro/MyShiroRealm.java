package cn.enter.config.shiro;

import cn.enter.dao.RedisDao;
import cn.enter.entity.Account;
import cn.enter.entity.Permission;
import cn.enter.entity.Role;
import cn.enter.service.IAccountService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *  身份校验核心类;
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
public class MyShiroRealm extends AuthorizingRealm{
	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	
//	@Override
//	public boolean supports(AuthenticationToken token) {
////		return super.supports(token);
//		////仅支持UsernamePasswordToken 类型的Token
//		return token instanceof UsernamePasswordToken;
//	}
	
	@Resource
	private IAccountService accountService;


	/**
	 * 认证信息.(身份验证) 
	 * :
	 * Authentication 是用来验证用户身份
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("MyShiroRealm.doGetAuthenticationInfo()");
		
		//获取用户的输入的账号.
		String username = (String)token.getPrincipal();
		logger.info(token.getCredentials().toString());
		
		//通过username从数据库中查找 Account对象，如果找到，没找到.
		//实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		Account account = accountService.findAccountByUsername(username);
		List<Role> roleList = account.getRoleList();
		Iterator<Role> it= roleList.iterator();
		logger.info("----->>account="+account);
		if(account == null){
			return null;
		}
		
		/*
		 * 获取权限信息:这里没有进行实现，
		 * 请自行根据Account,Role,Permission进行实现；
		 * 获取之后可以在前端for循环显示所有链接;
		 */

		//账号判断;
		
		//加密方式;
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				account, //用户
				account.getPassword(), //密码
                ByteSource.Util.bytes(account.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
		

		return authenticationInfo;
	}
	
	
	
	/**
	 * 此方法调用  hasRole,hasPermission的时候才会进行回调.
	 * 
	 * 权限信息.(授权):
	 * 1、如果用户正常退出，缓存自动清空；
	 * 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
	 * （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
	 * 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/*
		 * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
		 * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
		 * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
		 * 缓存过期之后会再次执行。
		 */
		logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Account account  = (Account)principals.getPrimaryPrincipal();
		
		//实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法

		for(Role role:account.getRoleList()){
			authorizationInfo.addRole(role.getRole());
			for(Permission p:role.getPermissions()){
				authorizationInfo.addStringPermission(p.getPermission());
			}
		}
		
		//设置权限信息.
		authorizationInfo.setStringPermissions(getStringPermissions(account.getRoleList()));
		
		return authorizationInfo;
	}
	
	
	/**
	 * 将权限对象中的 权限code取出.
	 * @param roles
	 * @return
	 */
	public Set<String> getStringPermissions(List<Role> roles){
		 Set<String> stringPermissions = new HashSet<String>();
		 Iterator<Role> it = roles.iterator();
		 while (it.hasNext()){
		 	Role role = it.next();
		 	Iterator<Permission> pIt = role.getPermissions().iterator();
		 	while (pIt.hasNext()){
		 		Permission permission = pIt.next();
		 		stringPermissions.add(permission.getPermission());
			}
		 }
	     return stringPermissions;
	}
	
}
