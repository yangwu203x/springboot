package cn.enter.exception;

import cn.enter.util.enums.RetCode;

/**
 * @Author leo_Yang【音特】
 * @Date 2017/10/17 0017 10:37
 */
public class EnterException extends RuntimeException {
    private Integer code;

    public EnterException(RetCode retCode) {
        super(retCode.getMsg());
        this.code = retCode.getCode();
    }

    public EnterException(RetCode retCode, String msg) {
        super(msg);
        this.code = retCode.getCode();
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
