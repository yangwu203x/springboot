package cn.enter.service.impl;

import cn.enter.entity.Song;
import cn.enter.repository.SongRepository;
import cn.enter.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author leo_Yang【音特】
 * @Date 2018/1/24 0024 14:21
 */
@Service
public class SongServiceImpl implements ISongService {
    @Autowired
    SongRepository songRepository;

    @Override
    public List<Song> listSong() {
        return songRepository.findAll();
    }
}
