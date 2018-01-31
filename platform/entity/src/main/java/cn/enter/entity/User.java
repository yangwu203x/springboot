package cn.enter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 14:02
 */
@Entity
@Table(name = "tb_user")
public class User implements Serializable{
    private static final long serialVersionUID = 8461995302357451551L;
    @Id
    @GeneratedValue
    private Long userId;
    private String username;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
