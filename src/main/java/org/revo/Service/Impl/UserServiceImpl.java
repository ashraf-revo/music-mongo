package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Domain.View;
import org.revo.Repository.UserRepository;
import org.revo.Service.CloudinaryService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

import static org.revo.Util.Util.addTo;
import static org.revo.Util.Util.key.*;
import static org.revo.Util.Util.removeFrom;

/**
 * Created by ashraf on 18/01/17.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MongoOperations mongoOperations;
    private final PasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MongoOperations mongoOperations, PasswordEncoder encoder, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
        this.encoder = encoder;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public User current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User)
            return (User) authentication.getPrincipal();
        else
            return new User();
    }

    @Override
    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty() && user.getPassword().length() != 60)
            user.setPassword(encoder.encode(user.getPassword()));
        if (user.getImage() != null && !user.getImage().isEmpty())
            user.setImageUrl(cloudinaryService.saveImage(user.getImage()));
        return userRepository.save(user);
    }

    @Override
    public void update(User user) throws Exception {
        Update update = new Update();
        User current = current();
        if (encoder.matches(user.getCurrentPassword(), current.getPassword())) {
            if (user.getInfo() != null && !user.getInfo().trim().isEmpty() && !Objects.equals(current.getInfo(), user.getInfo()))
                update.set("info", user.getInfo());
            if (user.getName() != null && !user.getName().trim().isEmpty() && !Objects.equals(current.getName(), user.getName()))
                update.set("name", user.getName());
            if (user.getPhone() != null && !user.getPhone().trim().isEmpty() && !Objects.equals(current.getPhone(), user.getPhone()))
                update.set("phone", user.getPhone());
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty() && !encoder.matches(current.getPassword(), user.getPassword()))
                update.set("password", encoder.encode(user.getPassword()));
            if (user.getImage() != null && !user.getImage().isEmpty())
                update.set("imageUrl", cloudinaryService.saveImage(user.getImage()));
            mongoOperations.updateFirst(new Query(Criteria.where("id").is(current.getId())), update, User.class);
        } else throw new Exception();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void song(User user, Song song) {
        addTo(mongoOperations, user.getId(), song, songs.getKey(), User.class);
    }

    public void like(User user, Like like) {
        addTo(mongoOperations, user.getId(), like, likes.getKey(), User.class);
    }

    @Override
    public void unLike(User user, Like like) {
        removeFrom(mongoOperations, user.getId(), like, likes.getKey(), User.class);
    }

    @Override
    public void view(User user, View view) {
        addTo(mongoOperations, user.getId(), view, views.getKey(), User.class);
    }

    @Override
    public User user(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void active(String id) {
        mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), new Update().set("enabled", "true"), User.class);
    }

    @Scheduled(cron = "0 0 */1 * * *")
    void ss() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 3);
        Criteria[] list = new Criteria[2];
        list[0] = Criteria.where("enabled").is(false);
        list[1] = Criteria.where("createdDate").lte(instance.getTime());
        Criteria c = new Criteria();
        Query query = new Query(c.andOperator(list));
        System.out.println("fired with " + query.toString());
        mongoOperations.remove(query, User.class);
    }
}
