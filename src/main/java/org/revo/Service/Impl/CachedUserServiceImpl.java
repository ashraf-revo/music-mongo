package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.View;
import org.revo.Repository.UserRepository;
import org.revo.Service.CachedUserService;
import org.revo.Service.UserService;
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
public class CachedUserServiceImpl implements CachedUserService {
    @Autowired
    private CachedUserService cachedUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Cacheable(value = "userViews", key = "#id")
    @Override
    public List<View> views(String id) {
        return userRepository.findByIdViews(id).getViews().stream().map(Util::copyView).collect(toList());
    }

    @CachePut(value = "userViews", key = "#view.user.id")
    @Override
    public List<View> add(View view) {
        List<View> views = cachedUserService.views(view.getUser().getId());
        views.add(copyView(view));
        return views;
    }

    @Cacheable(value = "userLikes", key = "#id")
    public List<Like> likes(String id) {
        return userRepository.findByIdLikes(id).getLikes().stream().map(Util::copyLike).collect(toList());
    }

    @CachePut(value = "userLikes", key = "#like.user.id")
    public List<Like> add(Like like) {
        List<Like> likes = cachedUserService.likes(like.getUser().getId());
        likes.add(copyLike(like));
        return likes;
    }

    @CachePut(value = "userLikes", key = "#like.user.id")
    public List<Like> remove(Like like) {
        return cachedUserService.likes(like.getUser().getId()).stream().filter(it -> !Objects.equals(it.getId(), like.getId())).collect(toList());
    }

    @Override
    public List<Like> likesByCurrentUser() {
        return cachedUserService.likes(userService.current().getId());
    }
}
