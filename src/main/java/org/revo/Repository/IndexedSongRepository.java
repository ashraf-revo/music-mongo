package org.revo.Repository;

import org.revo.Domain.IndexedSong;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by ashraf on 22/01/17.
 */
public interface IndexedSongRepository extends ElasticsearchRepository<IndexedSong, String> {
}
   