package cn.enter.util.enums;

/**
 * Created by yangwu on 2017/3/28 0028.
 */
public enum DataType {
    Constant(0,"常量"),
    Object(1,"对象"),
    Array(2,"数组");

    DataType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    private int type;
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public int getType() {
        return type;
    }

}
