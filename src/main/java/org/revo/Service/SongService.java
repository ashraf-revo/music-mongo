package org.revo.Service;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.View;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
public interface SongService {
    List<Song> findAll();

    List<Song> findAllTo(String id);

    Song save(Song song);

    boolean exist(String id);

    void like(Song song, Like like);

    void unLike(Song song, Like like);

    void view(Song song, View view);

    List<Song> find(List<String> songs);

    List<Song> findByLikesIn(List<Like> likes);

    List<Song> findByViewsIn(List<View> views);
}