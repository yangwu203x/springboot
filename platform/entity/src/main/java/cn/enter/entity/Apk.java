package cn.enter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/30 0030 10:11
 */
@Entity
@Table(name = "tb_apk")
public class Apk implements Serializable{
    private static final long serialVersionUID = 1346993168407396665L;
    @Id
    @GeneratedValue
    private Integer id;

    private String apkName;

    private String apkVersion;

    private String apkPath;

    private String serverUrl;

    private String apkDownPath;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getApkDownPath() {
        return apkDownPath;
    }

    public void setApkDownPath(String apkDownPath) {
        this.apkDownPath = apkDownPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
