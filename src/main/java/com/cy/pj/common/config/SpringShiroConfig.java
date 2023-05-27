package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**@Configuration 注解描述的类为一个配置对象
 * 此对象也会交给spring管理
 * @Bean注解没有指定其value属性的值，则bean的名字默认为方法名
 *
 */
@Configuration
public class SpringShiroConfig {//spring -shiro.xml
	/**
	 * Spring容器在管理ShiroFilterFactoryBean对象，
	 * 会基于ShiroFilterFactoryBean对象，创建
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(Realm realm,CacheManager cacheManager,RememberMeManager rememberManager,SessionManager sessionManager) 
	{
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setCacheManager(cacheManager);
		sManager.setRememberMeManager(rememberManager);
		sManager.setSessionManager(sessionManager);
		return sManager;
	}
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) 
	{
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		//设置需要进行认证的登录页面
		sfBean.setLoginUrl("/doLoginUI");
		//定义map指定请求过滤规则(哪些资源不允许匿名访问，哪些必须认证访问)
		LinkedHashMap<String , String> map = new LinkedHashMap<>();
		//静态资源允许匿名访问："anon"
		map.put("/bower_components/**", "anon");
		map.put("/build/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/user/doLogin", "anon");
		map.put("/doLogout", "logout");

		
		//map.put("/**", "authc");除了可以匿名访问的资源，其他都要认证("authc")后访问
		map.put("/**", "user");//表示可以通过用户端提交的cookie信息进行认证
		sfBean.setFilterChainDefinitionMap(map);
		
		return sfBean;
	}
	//配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值(类似切入点)决定是否创建代理对象,进行权限控制。
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) 
	{
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	//配置缓存，缓存获取权限的信息
	@Bean
	public CacheManager shiroCacheManager() 
	{
		return new MemoryConstrainedCacheManager();
	}
	@Bean
	public RememberMeManager rememberMeManager() 
	{
		CookieRememberMeManager cManager = new CookieRememberMeManager();
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(7*24*60*60);
		cManager.setCookie(cookie);
		//设置加密解密秘钥(假如服务器重启以后，还是需要重新登录，添加如下语句)
		//1)秘钥选择是16位的字符串
		//2)秘钥复杂性越高，加密以后的内容就越安全
		//cManager.setCipherKey("1234567890123456".getBytes());
		return cManager;
	}
	@Bean
	public SessionManager sessionManager() 
	{
		DefaultWebSessionManager sManager = new DefaultWebSessionManager();
		sManager.setGlobalSessionTimeout(60*60*1000);
		return sManager;
	}
}
