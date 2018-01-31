package cn.enter.config.shiro.filter;

import cn.enter.dao.RedisDao;
import cn.enter.util.enums.RetCode;
import cn.enter.util.protocol.BodyUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/31 0031 10:28
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {


    @Autowired
    private RedisDao redisDao;
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        redisDao.setKV("jcaptchaEbabled", "true");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        //判断是不是登录表单提交
        if (!("post".equalsIgnoreCase(method) && uri.contains("/login"))) {
            return true;
        }
        // 校验验证码
        // 从redis获取正确的验证码
        //页面输入的验证码
        String randomcode = request.getParameter("authCode");
        String sessionId = httpServletRequest.getSession().getId();
        //从redis中取出验证码
        String validateCode =  redisDao.getKV(sessionId);
        if (randomcode != null && validateCode != null && !randomcode.equalsIgnoreCase(validateCode)) {
            // randomCodeError表示验证码错误
            request.setAttribute("shiroLoginFailure", "randomCodeError");
            //拒绝访问，不再校验账号和密码
            return true;
        }

        return super.onAccessDenied(request, response, mappedValue);
    }

    /**
     * 登录成功的处理
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            issueSuccessRedirect(request, response);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.println(BodyUtil.success().toString());
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 登录失败的处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                out.println(BodyUtil.error(RetCode.ERROR_PASSWORD));
            } else if ("UnknownAccountException".equals(message)) {
                out.println(BodyUtil.error(RetCode.USERNOTREGISTER));
            } else if ("LockedAccountException".equals(message)) {
                out.println(BodyUtil.error(RetCode.LOCKED));
            } else {
                out.println(BodyUtil.error(RetCode.UNKNOWN_ERROR));
            }
            out.flush();
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }
}
