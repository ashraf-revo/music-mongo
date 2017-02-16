package org.revo.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.Domain.Role;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Service.CachedUserService;
import org.revo.Service.MailService;
import org.revo.Service.SongService;
import org.revo.Service.UserService;
import org.revo.Util.UniqueEmail;
import org.revo.Util.ViewDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ashraf on 21/01/17.
 */
@RestController
@RequestMapping(Role.Paths.USER_PATH)
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CachedUserService cachedUserService;
    @Autowired
    private SongService songService;

    @GetMapping
    @JsonView(ViewDetails.user.class)
    public ResponseEntity<User> user() {
        return ResponseEntity.ok(userService.current());
    }

    @GetMapping("{id}")
    @JsonView(ViewDetails.user.class)
    public ResponseEntity<User> userId(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.user(id));
    }

    @GetMapping("{id}/songs")
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<List<Song>> songsTo(@PathVariable("id") String id) {
        return ResponseEntity.ok(songService.findAllTo(id));
    }

    @GetMapping("{id}/likes")
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<List<Song>> likes(@PathVariable("id") String id) {
        return ResponseEntity.ok(songService.findByLikesIn(cachedUserService.likes(id)));
    }

    @GetMapping("{id}/views")
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<List<Song>> views(@PathVariable("id") String id) {
        return ResponseEntity.ok(songService.findByViewsIn(cachedUserService.views(id)));
    }

    @GetMapping("{id}/active")
    public ResponseEntity active(@PathVariable("id") String id) {
        userService.active(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @JsonView(ViewDetails.user.class)
    public ResponseEntity save(@Validated @Valid @ModelAttribute User user, BindingResult bindingResult) throws BindException {
        new UniqueEmail(userService).validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        User save = userService.save(user);
        mailService.SendActivation(save);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update")
    @JsonView(ViewDetails.user.class)
    public ResponseEntity update(@ModelAttribute User user) {
        try {
            userService.update(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}