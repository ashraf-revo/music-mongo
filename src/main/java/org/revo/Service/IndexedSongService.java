package org.revo.Service;

import org.revo.Domain.SearchCriteria;
import org.revo.Domain.Song;
import org.springframework.data.domain.Page;

/**
 * Created by ashraf on 22/01/17.
 */
public interface IndexedSongService {
    Page<Song> search(SearchCriteria searchCriteria);

    void save(Song song);
}