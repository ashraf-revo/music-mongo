package org.revo.Repository;

import org.revo.Domain.View;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ashraf on 22/01/17.
 */
public interface ViewRepository extends MongoRepository<View, String> {
}