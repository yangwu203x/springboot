package cn.enter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/30 0030 10:23
 */
@Entity
@Table(name = "tb_lang")
public class Lang implements Serializable{

    private static final long serialVersionUID = 3883014237886176142L;
    @Id
    @GeneratedValue
    private Long langId;
    private String langName;

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
}
