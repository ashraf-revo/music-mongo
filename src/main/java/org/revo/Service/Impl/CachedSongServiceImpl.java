package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.View;
import org.revo.Repository.SongRepository;
import org.revo.Service.CachedSongService;
import org.revo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.revo.Util.Util.copyLike;
import static org.revo.Util.Util.copyView;

/**
 * Created by ashraf on 15/02/17.
 */
@Service
public class CachedSongServiceImpl implements CachedSongService {
    private final CachedSongService cachedSongService;
    private final SongRepository songRepository;

    @Autowired
    public CachedSongServiceImpl(CachedSongService cachedSongService, SongRepository songRepository) {
        this.cachedSongService = cachedSongService;
        this.songRepository = songRepository;
    }

    @Cacheable(value = "songViews", key = "#id")
    @Override
    public List<View> views(String id) {
        return songRepository.findByIdViews(id).getViews().stream().map(Util::copyView).collect(toList());
    }

    @CachePut(value = "songViews", key = "#view.song.id")
    @Override
    public List<View> add(View view) {
        List<View> views = cachedSongService.views(view.getSong().getId());
        views.add(copyView(view));
        return views;
    }

    @Cacheable(value = "songLikes", key = "#id")
    public List<Like> likes(String id) {
        return songRepository.findByIdLikes(id).getLikes().stream().map(Util::copyLike).collect(toList());
    }

    @CachePut(value = "songLikes", key = "#like.song.id")
    public List<Like> add(Like like) {
        List<Like> likes = cachedSongService.likes(like.getSong().getId());
        likes.add(copyLike(like));
        return likes;
    }

    @CachePut(value = "songLikes", key = "#like.song.id")
    public List<Like> remove(Like like) {
        return cachedSongService.likes(like.getSong().getId()).stream().filter(it -> !Objects.equals(it.getId(), like.getId())).collect(toList());
    }

}
