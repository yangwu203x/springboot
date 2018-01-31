package cn.enter.util.protocol;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by yangwu on 2017/3/21 0021.
 */
public class Body<T>{
    /**
     * 返回代码
     * 参见枚举类RetCode
     */
    private int code;
    private String msg;

    /**
     * 要跳转的链接
     */
    private String url;
    /**
     * 0:常量，1:json对象，2:json对象数组
     */
    private int type;
    /**
     * 返回数据
     */
    private T data;

    public Body() {
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
