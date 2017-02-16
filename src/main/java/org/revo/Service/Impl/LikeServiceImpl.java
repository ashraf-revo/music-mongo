package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Repository.LikeRepository;
import org.revo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by ashraf on 18/01/17.
 */
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final SongService songService;
    private final CachedSongService cachedSongService;
    private final CachedUserService cachedUserService;
    private final MongoOperations mongoOperations;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, SongService songService, CachedSongService cachedSongService, CachedUserService cachedUserService, MongoOperations mongoOperations) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.songService = songService;
        this.cachedSongService = cachedSongService;
        this.cachedUserService = cachedUserService;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Like like(Like like) {
        Assert.isNull(like.getId());
        Assert.notNull(like.getSong().getId());
        Assert.isTrue(songService.exist(like.getSong().getId()));
        like.setUser(userService.current());
        return likeRepository.findByUser_IdAndSong_Id(like.getUser().getId(), like.getSong().getId())
                .orElseGet(() -> {
                    Like save = likeRepository.save(like);
                    userService.like(like.getUser(), save);
                    songService.like(like.getSong(), save);
                    cachedUserService.add(save);
                    cachedSongService.add(save);
                    return save;
                });
    }

    @Override
    public void unLike(Like like) {
        Assert.notNull(like.getId());
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(like.getId()), Criteria.where("user").is(userService.current()));
        Like andRemove = mongoOperations.findAndRemove(new Query(criteria), Like.class);
        if (andRemove != null) {
            userService.unLike(andRemove.getUser(), andRemove);
            songService.unLike(andRemove.getSong(), andRemove);
            cachedUserService.remove(andRemove);
            cachedSongService.remove(andRemove);

        }
    }
}