package cn.enter.controller;

import cn.enter.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/31 0031 18:08
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/listPermission")
    public String listPermission(){
        return "/permission/permission_list";
    }
}
