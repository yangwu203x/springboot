package cn.enter.hanlder;

import cn.enter.exception.EnterException;
import cn.enter.util.enums.RetCode;
import cn.enter.util.protocol.BodyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 捕获返回全局异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);




    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandlerBody(HttpServletRequest req, Exception e) {
        if(e instanceof EnterException){
            EnterException enterExcetpion = (EnterException) e;
            if (enterExcetpion.getCode() == RetCode.UNLOGINED.getCode())
                return BodyUtil.unLoginException();
            return BodyUtil.exception(enterExcetpion.getCode(),enterExcetpion.getMessage());
        }else{
            logger.info(e.getMessage());
            return BodyUtil.exception(RetCode.UNKNOWN_ERROR.getCode(),RetCode.UNKNOWN_ERROR.getMsg());
        }
    }

}

