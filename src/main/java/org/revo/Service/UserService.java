package org.revo.Service;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Domain.View;

import java.util.Optional;

/**
 * Created by ashraf on 18/01/17.
 */
public interface UserService {
    User save(User user);

    void update(User user) throws Exception;

    User current();

    long count();

    void song(User user, Song song);

    void like(User user, Like like);

    void unLike(User user, Like like);

    void view(User user, View view);

    User user(String id);

    Optional<User> findByEmail(String email);

    void active(String id);
}