package cn.enter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/25 0025 9:28
 */
@Entity
@Table(name = "tb_singer_type")
public class SingerType implements Serializable{
    private static final long serialVersionUID = 624675807361242847L;

    @Id
    @GeneratedValue
    private Long singerTypeId;
    private String singerTypeName;

    public Long getSingerTypeId() {
        return singerTypeId;
    }

    public void setSingerTypeId(Long singerTypeId) {
        this.singerTypeId = singerTypeId;
    }

    public String getSingerTypeName() {
        return singerTypeName;
    }

    public void setSingerTypeName(String singerTypeName) {
        this.singerTypeName = singerTypeName;
    }
}
