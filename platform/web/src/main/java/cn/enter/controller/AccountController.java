package cn.enter.controller;

import cn.enter.dto.SortDto;
import cn.enter.entity.Account;
import cn.enter.entity.Permission;
import cn.enter.entity.Role;
import cn.enter.service.IAccountService;
import cn.enter.util.StringUtils;
import cn.enter.util.enums.RetCode;
import cn.enter.util.page.PageableTools;
import cn.enter.util.protocol.BodyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/26 0026 14:21
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping({"/","/index"})
    public String index(Model model){
        Subject subject = SecurityUtils.getSubject();
        Account account = (Account) subject.getPrincipal();
        Set<Permission> permissions = new HashSet<>();
        Iterator<Role> it = account.getRoleList().iterator();
        while (it.hasNext()){
            Role role = it.next();
            Iterator<Permission> pIt = role.getPermissions().iterator();
            while (pIt.hasNext()){
                Permission permission = pIt.next();
                permissions.add(permission);
            }
        }
        Set<Permission> perSet = Permission.resolvePermission(permissions,0l);
        model.addAttribute("permissions",perSet);
        return "/index";
    }



    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response,HttpServletRequest request) throws IOException {
        accountService.captcha(response,request);
    }

    // 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, Map<String, Object> map) {
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");

        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }else{
            return BodyUtil.success();
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理.
        return BodyUtil.error(RetCode.UNLOGINED);
    }

    @GetMapping("/listAccount")
    public String listAccount(Account account , Model model,HttpServletRequest request){
        String pageStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");
        //默认取第一页，取10条
        int pageNum = 1,pageSize = 10;
        if (StringUtils.isNumeric(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (StringUtils.isNumeric(pageStr)){
            pageNum = Integer.parseInt(pageStr);
        }
        SortDto dto = new SortDto("desc","uid");
        Pageable pageable =  PageableTools.basicPage(pageNum-1,pageSize,dto);
        Page<Account> page = accountService.findAll(pageable);
        model.addAttribute("accountList",page.getContent());
        model.addAttribute("totalNum",page.getTotalElements());
        model.addAttribute("pageNum",pageable.getPageNumber()+1);
        model.addAttribute("pageSize",pageable.getPageSize());
        return "account/account_list";
    }



    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
