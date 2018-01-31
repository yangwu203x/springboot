package cn.enter.repository;

import cn.enter.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 14:56
 */
public interface SongRepository extends JpaRepository<Song,Long>{
    List<Song> findAll();
}
