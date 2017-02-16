package org.revo.Repository;

import org.revo.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Created by ashraf on 18/01/17.
 */
public interface UserRepository extends MongoRepository<User, String> {
    @Query(value = "{'email':?0}", fields = "{'songs':0,'likes':0,'views':0}")
    Optional<User> findByEmail(String email);

    @Query(value = "{'id':?0}", fields = "{'views':1}")
    User findByIdViews(String id);

    @Query(value = "{'id':?0}", fields = "{'likes':1}")
    User findByIdLikes(String id);

}