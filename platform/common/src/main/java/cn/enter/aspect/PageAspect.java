package cn.enter.aspect;

import cn.enter.util.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页切面
 * Created by yangwu on 2017/3/31 0031.
 */
@Aspect
@Component
public class PageAspect {

    private final static Logger logger = LoggerFactory.getLogger(PageAspect.class);

    @Pointcut("@annotation(cn.enter.annotation.UsePage)")
    public void pageAspect(){

    }

    @Before("pageAspect()")
    public void doBefore(){
        logger.info("============================================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String pageStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");
        logger.info("pageNum={}",pageStr);
        logger.info("pageSize={}",pageSizeStr);
        //默认取第一页，取10条
        int pageNum = 1,pageSize = 10;
        if (StringUtils.isNumeric(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (StringUtils.isNumeric(pageStr)){
            pageNum = Integer.parseInt(pageStr);
        }

    }

    @After("pageAspect()")
    public void doAfter(){
        logger.info("============================================");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


    }

}
