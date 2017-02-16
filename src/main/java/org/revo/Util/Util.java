package org.revo.Util;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Domain.View;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by ashraf on 18/01/17.
 */
public class Util {
    public static <T> void addTo(MongoOperations mongoOperations, String id, T t, String key, Class aClass) {
        mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), new Update().push(key, t), aClass);
    }

    public static <T> void removeFrom(MongoOperations mongoOperations, String id, T t, String key, Class aClass) {
        mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), new Update().pull(key, t), aClass);
    }

    public enum key {
        songs("uploaded"), likes("likes"), views("views");
        private String key;

        key(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    static public List<Song> buildLikes(List<Song> songs, List<Like> likes) {
        Map<String, Like> collect = likes.stream().collect(toMap(like -> like.getSong().getId(), identity()));
        return songs.stream().map(song -> {
            if (collect.containsKey(song.getId())){

                Like liked = collect.get(song.getId());
                liked.setSong(song);

                song.setLiked(liked);}
            return song;
        }).collect(toList());
    }
    public static View copyView(View view) {
        View v = new View();
        v.setId(view.getId());
        Song song = new Song();
        song.setId(view.getSong().getId());
        v.setSong(song);
        v.setId(view.getId());
        User user = new User();
        user.setId(view.getUser().getId());
        v.setUser(user);
        return v;
    }

    public static Like copyLike(Like like) {
        Like l = new Like();
        l.setId(like.getId());
        Song song = new Song();
        song.setId(like.getSong().getId());
        l.setSong(song);
        l.setId(like.getId());
        User user = new User();
        user.setId(like.getUser().getId());
        l.setUser(user);
        return l;
    }

}
