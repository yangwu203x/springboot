package cn.enter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 10:10
 */
@Entity
@Table(name = "tb_hot_song")
public class HotSong implements Serializable{
    private static final long serialVersionUID = -3929834596512845541L;

    @Id
    @GeneratedValue
    private Long id;
    private Long songId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }
}
