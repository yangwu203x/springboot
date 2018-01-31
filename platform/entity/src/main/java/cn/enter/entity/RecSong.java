package cn.enter.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 10:10
 */
@Entity
@Table(name = "tb_rec_song")
public class RecSong implements Serializable{
    private static final long serialVersionUID = -3372761314494687543L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
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
