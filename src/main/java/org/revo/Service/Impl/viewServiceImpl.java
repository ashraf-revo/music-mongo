package org.revo.Service.Impl;

import org.revo.Domain.View;
import org.revo.Repository.ViewRepository;
import org.revo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by ashraf on 22/01/17.
 */
@Service
public class viewServiceImpl implements ViewService {
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private SongService songService;
    @Autowired
    private UserService userService;
    @Autowired
    private CachedSongService cachedSongService;
    @Autowired
    private CachedUserService cachedUserService;


    @Override
    public View view(View view) {
        Assert.isNull(view.getId());
        Assert.notNull(view.getSong().getId());
        view.setUser(userService.current());
        View v = viewRepository.save(view);
        userService.view(v.getUser(), v);
        songService.view(v.getSong(), v);
        cachedUserService.add(v);
        cachedSongService.add(v);
        return v;
    }
}
