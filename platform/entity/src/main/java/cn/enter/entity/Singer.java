package cn.enter.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 10:07
 */
@Entity
@Table(name = "tb_singer")
public class Singer implements Serializable{
    private static final long serialVersionUID = -227907895285248393L;

    @Id
    @GeneratedValue
    private Long singerId;
    private String singName;
    private Integer singerWords;
    private String singerSpell;
    @ManyToOne
    @JoinColumn(name = "singer_type_id")
    private SingerType singerType;//歌手类型

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public String getSingName() {
        return singName;
    }

    public void setSingName(String singName) {
        this.singName = singName;
    }

    public Integer getSingerWords() {
        return singerWords;
    }

    public void setSingerWords(Integer singerWords) {
        this.singerWords = singerWords;
    }

    public String getSingerSpell() {
        return singerSpell;
    }

    public void setSingerSpell(String singerSpell) {
        this.singerSpell = singerSpell;
    }

    public SingerType getSingerType() {
        return singerType;
    }

    public void setSingerType(SingerType singerType) {
        this.singerType = singerType;
    }

}
