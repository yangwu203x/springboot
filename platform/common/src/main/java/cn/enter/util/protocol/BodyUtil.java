package cn.enter.util.protocol;


import cn.enter.util.enums.DataType;
import cn.enter.util.enums.RetCode;

/**
 * Created by yangwu on 2017/3/28 0028.
 */
public class BodyUtil {

    public static Body result(int result){
        if (result > 0){
            return success();
        }else{
            return error(RetCode.UNKNOWN_ERROR);
        }
    }
    /**
     * 成功
     * @param object
     * @param dataType
     * @return
     */
    public static Body success(Object object , DataType dataType){
        return success(object,dataType,null);
    }

    /**
     * 成功
     * @param object
     * @param dataType
     * @return
     */
    public static Body success(Object object , DataType dataType, String url){
        Body body = new Body();
        body.setCode(RetCode.SUCCESS.getCode());
        body.setMsg(RetCode.SUCCESS.getMsg());
        body.setType(dataType.getType());
        body.setData(object);
        body.setUrl(url);
        return body;
    }

    public static Body success(){
        return success(null,DataType.Constant);
    }

    /**
     * 异常返回
     * @param code
     * @param message
     * @return
     */
    public static Body exception(Integer code,String message){
        Body body = new Body();
        body.setCode(code);
        body.setMsg(message);
        body.setType(DataType.Object.getType());
        body.setData(null);
        return body;
    }

    public static Body unLoginException(){
        Body body = new Body();
        body.setCode(RetCode.UNLOGINED.getCode());
        body.setMsg(RetCode.UNLOGINED.getMsg());
        body.setType(DataType.Object.getType());
        body.setData(null);
        body.setUrl("/login");
        return body;
    }


    /**
     * 失败
     * @param retCode
     * @return
     */
    public static Body error(RetCode retCode){
        Body body = new Body();
        body.setCode(retCode.getCode());
        body.setMsg(retCode.getMsg());
        body.setType(DataType.Object.getType());
        body.setData(null);
        return body;
    }
}
