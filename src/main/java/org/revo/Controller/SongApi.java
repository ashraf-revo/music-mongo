package org.revo.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.Domain.*;
import org.revo.Service.*;
import org.revo.Util.Util;
import org.revo.Util.ViewDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
@RestController
@RequestMapping(Role.Paths.SONG_PATH)
public class SongApi {
    private final SongService songService;
    private final LikeService likeService;
    private final IndexedSongService indexedSongService;
    private final ViewService viewService;
    private final
    CachedUserService cachedUserService;

    @Autowired
    public SongApi(SongService songService, LikeService likeService, IndexedSongService indexedSongService, ViewService viewService, CachedUserService cachedUserService) {
        this.songService = songService;
        this.likeService = likeService;
        this.indexedSongService = indexedSongService;
        this.viewService = viewService;
        this.cachedUserService = cachedUserService;
    }

    @GetMapping
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<List<Song>> songs() {
        return ResponseEntity.ok(Util.buildLikes(songService.findAll(), cachedUserService.likesByCurrentUser()));
    }

    @PostMapping(value = "search")
    public ResponseEntity<Page<Song>> search(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(indexedSongService.search(searchCriteria));
    }

    @PostMapping
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<Song> song(@ModelAttribute Song song) {
        return ResponseEntity.ok(songService.save(song));
    }

    @PostMapping("like")
    @JsonView(ViewDetails.CustomLike.class)
    public ResponseEntity<Like> like(@Validated @RequestBody Like like) {
        return ResponseEntity.ok(likeService.like(like));
    }

    @PostMapping("unlike")
    public ResponseEntity<Void> unlike(@RequestBody Like like) {
        likeService.unLike(like);
        return ResponseEntity.ok().build();
    }

    @PostMapping("view")
    @JsonView(ViewDetails.CustomView.class)
    public ResponseEntity view(@RequestBody @Validated View view) {
        viewService.view(view);
        return ResponseEntity.ok().build();
    }
}