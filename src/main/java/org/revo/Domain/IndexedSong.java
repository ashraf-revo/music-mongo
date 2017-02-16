package org.revo.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by ashraf on 22/01/17.
 */

@Document(indexName = "song")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexedSong {
    @Id
    private String id;
    private String title;
    private String description;

    public static IndexedSong indexedSong(Song song) {
        return new IndexedSong(song.getId(), song.getTitle(), song.getDescription());
    }
}