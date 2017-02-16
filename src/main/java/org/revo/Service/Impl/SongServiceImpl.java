package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Domain.View;
import org.revo.Repository.SongRepository;
import org.revo.Service.CloudinaryService;
import org.revo.Service.IndexedSongService;
import org.revo.Service.SongService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.revo.Util.Util.addTo;
import static org.revo.Util.Util.key.likes;
import static org.revo.Util.Util.key.views;
import static org.revo.Util.Util.removeFrom;

/**
 * Created by ashraf on 18/01/17.
 */
@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final MongoOperations mongoOperations;
    private final UserService userService;
    private final IndexedSongService indexedSongService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, MongoOperations mongoOperations, UserService userService, IndexedSongService indexedSongService, CloudinaryService cloudinaryService) {
        this.songRepository = songRepository;
        this.mongoOperations = mongoOperations;
        this.userService = userService;
        this.indexedSongService = indexedSongService;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public List<Song> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return songRepository.findAll();
        } else {
            return songRepository.findAll();
        }
    }

    @Override
    public List<Song> findAllTo(String id) {
        return songRepository.findByUser_Id(id);
    }

    @Override
    public Song save(Song song) {
        Assert.isNull(song.getId());
        if (song.getImage() != null && !song.getImage().isEmpty())
            song.setImageUrl(cloudinaryService.saveImage(song.getImage()));
        if (song.getFile() != null && !song.getFile().isEmpty())
            song.setFileUrl(cloudinaryService.saveFile(song));
        song = songRepository.save(song);
        userService.song(song.getUser(), song);
        indexedSongService.save(song);
        return song;
    }

    @Override
    public void like(Song song, Like like) {
        addTo(mongoOperations, song.getId(), like, likes.getKey(), Song.class);
    }

    @Override
    public void unLike(Song song, Like like) {
        removeFrom(mongoOperations, song.getId(), like, likes.getKey(), Song.class);
    }

    @Override
    public void view(Song song, View view) {
        addTo(mongoOperations, song.getId(), view, views.getKey(), Song.class);
    }

    @Override
    public List<Song> find(List<String> songs) {
        return songRepository.findAll(songs);
    }

    @Override
    public List<Song> findByLikesIn(List<Like> likes) {
        return songRepository.findByLikesIn(likes);
    }

    @Override
    public List<Song> findByViewsIn(List<View> views) {
        return songRepository.findByViewsIn(views);
    }

    @Override
    public boolean exist(String id) {
        return songRepository.exists(id);
    }
}