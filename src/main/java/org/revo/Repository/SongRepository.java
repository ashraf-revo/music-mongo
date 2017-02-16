package org.revo.Repository;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.View;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
public interface SongRepository extends MongoRepository<Song, String> {
    List<Song> findAll(Iterable<String> ids);

    List<Song> findByUser_Id(String id);

    @Query(fields = "{'views':0,'likes':0}")
    List<Song> findByLikesIn(List<Like> likes);

    @Query(fields = "{'views':0,'likes':0}")
    List<Song> findByViewsIn(List<View> views);

    @Query(value = "{'id':?0}", fields = "{'views':1}")
    Song findByIdViews(String id);

    @Query(value = "{'id':?0}", fields = "{'likes':1}")
    Song findByIdLikes(String id);
}