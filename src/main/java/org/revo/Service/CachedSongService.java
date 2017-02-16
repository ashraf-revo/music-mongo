package org.revo.Service;

import org.revo.Domain.Like;
import org.revo.Domain.View;

import java.util.List;

/**
 * Created by ashraf on 15/02/17.
 */
public interface CachedSongService {
    List<View> views(String id);

    List<View> add(View view);

    List<Like> likes(String id);

    List<Like> add(Like like);

    List<Like> remove(Like like);

}
