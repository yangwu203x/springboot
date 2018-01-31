package cn.enter.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 10:07
 */
@Entity
@Table(name = "tb_song")
public class Song implements Serializable {
    private static final long serialVersionUID = 3691132488843545349L;

    @Id
    @GeneratedValue
    private Long songId;//歌曲编号
    private String songName;//歌曲名
    private String songLocalUrl;//以便手机端解析
    private String songServerUrl;//歌曲服务端地址
    private Integer songClicks;//歌曲点击次数
    private Integer songVersion;//歌曲版本
    private Integer songFormat;//歌曲格式
    private Integer songWords;//歌曲字数
    private String songSpell;//歌曲名首字母拼接

    private Integer langId;//语种id
    private String singerId;//歌手id

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongLocalUrl() {
        return songLocalUrl;
    }

    public void setSongLocalUrl(String songLocalUrl) {
        this.songLocalUrl = songLocalUrl;
    }

    public String getSongServerUrl() {
        return songServerUrl;
    }

    public void setSongServerUrl(String songServerUrl) {
        this.songServerUrl = songServerUrl;
    }

    public Integer getSongClicks() {
        return songClicks;
    }

    public void setSongClicks(Integer songClicks) {
        this.songClicks = songClicks;
    }

    public Integer getSongVersion() {
        return songVersion;
    }

    public void setSongVersion(Integer songVersion) {
        this.songVersion = songVersion;
    }

    public Integer getSongFormat() {
        return songFormat;
    }

    public void setSongFormat(Integer songFormat) {
        this.songFormat = songFormat;
    }

    public Integer getSongWords() {
        return songWords;
    }

    public void setSongWords(Integer songWords) {
        this.songWords = songWords;
    }

    public String getSongSpell() {
        return songSpell;
    }

    public void setSongSpell(String songSpell) {
        this.songSpell = songSpell;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }
}
