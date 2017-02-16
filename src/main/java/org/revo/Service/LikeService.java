package org.revo.Service;

import org.revo.Domain.Like;

/**
 * Created by ashraf on 18/01/17.
 */
public interface LikeService {
    Like like(Like like);

    void unLike(Like like);
}
   