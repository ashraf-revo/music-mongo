package org.revo.Repository;

import org.revo.Domain.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by ashraf on 18/01/17.
 */
public interface LikeRepository extends MongoRepository<Like, String> {

    Optional<Like> findByUser_IdAndSong_Id(String id1, String id2);
}
   