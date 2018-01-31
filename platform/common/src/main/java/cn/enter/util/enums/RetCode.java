package cn.enter.util.enums;

/**
 * Created by yangwu on 2017/3/17 0017.
 */
public enum RetCode {

    SUCCESS(1,"success"),
    FAILED(-1,"失败"),
    UNKNOWN_ERROR(0,"未知错误"),
    UNLOGINED(2,"未登陆"),
    UNACTIVED(3,"账号未激活"),
    PARAMETER_ERROR(1001,"参数错误"),
    UTIL_ILLEGAL(4,"非法操作,当前登录用户操作了其他用户数据"),
    UTIL_AUTHCODE(5,"验证码错误"),
    ERROR_PASSWORD(6,"密码错误"),
    NEWVERSION_NO(7,"没有新版本"),
    SQLEXCEPTION(8,"数据库异常"),
    LOG_USERNAME_OR_PASSWOLD_ERROR(9,"用户名或密码错误"),
    USERNOTREGISTER(10,"用户未注册"),
    TOKENISEXPIRE(11,"token已过期"),
    LOCKED(12,"账号被锁"),
    REG_USER_PARAMETER_ERROR(101,"用户参数错误"),
    REG_EMPTY_EMAIL(102,"邮箱为空"),
    REG_FORMAT_ERROR_EMAIL(103,"邮箱格式不正确"),
    REG_EXIST_EMAIL(104,"邮箱已经注册"),
    REG_EMPTY_USER_NAME(105,"用户名为空"),
    REG_EXIST_USER_NAME(106,"用户名已存在"),
    REG_EMPTY_PASSWORD(107,"密码为空"),
    REG_EMPTY_PHONENUM(108,"手机号为空"),
    REG_FORMAT_ERROR_PHONENUM(109,"手机格式不正确"),
    REG_EXIST_PHONENUM(110,"手机号已存在"),
    REG_EMPTY_AUTHENTICATED_CODE(111,"验证码为空"),
    REG_AUTHENTICATED_CODE_ERROR(112,"验证码错误"),
    REG_MISTAKE_ACTIVATION(113,"邮箱激活码错误"),
    REG_EMAIL_ACTIVATED(114,"邮箱已激活"),
    REG_EMAIL_IS_NOT_REGISTER(115,"邮箱未注册"),
    REG_EMAIL_IS_MISTAKE(116,"邮箱格式有误"),
    REG_EMAIL_MORETHANONE(117,"存在多个邮箱相同的账号"),
    PLEASE_SEND_VALID(10001,"请先发送验证码"),
    TROLLEYISNOTEXIST(20001,"购物车id对应的物品不存在"),
    ADDRESSNOTEXIST(30001,"addressId对应的地址不存在"),
    DXCODEISNULL(40001,"短信验证码为空"),
    DXCODEMISTAKE(40002,"短信验证码错误"),
    ORDERNUMNOTEXIST(50001,"订单编号对应的订单不存在"),
    SERVICENOTEXIST(60001,"查询的售后服务不存在"),
    SERVICECANCERFAILED(60002,"要取消的售后服务不存在"),
    PRODUCTNOTEXIST(70001,"产品不存在"),
    PRODUCTNOTENOUGH(70002,"产品库存不够"),
    COOKIESNOTALLOW(80002,"cookies不被允许，请修改"),
    MULTIPARTFILENOTEXIST(90001,"MULTIPARTFILE文件不存在"),
    STATUSEXCEPTION(118,"账号状态异常"),
    USERNOTEXIST(119,"用户不存在");


    RetCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
