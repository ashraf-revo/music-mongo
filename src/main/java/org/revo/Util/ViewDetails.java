package org.revo.Util;

/**
 * Created by ashraf on 20/01/17.
 */
public class ViewDetails {
    public interface user {
    }

    public interface userSongs {
    }

    public interface userLikes {
    }

    public interface userViews {
    }

    public interface song {
    }

    public interface songUser {
    }

    public interface songLikes {
    }

    public interface songViews {
    }

    public interface like {
    }

    public interface likeUser {
    }

    public interface likeSong {
    }

    public interface view {
    }

    public interface viewUser {
    }

    public interface viewSong {
    }

    public interface CustomSong extends song, songUser, user, like {
    }

    public interface CustomLike extends like, likeSong, song, likeUser, user, songUser {
    }

    public interface CustomView extends view, viewSong, song, viewUser, user, songUser {
    }
}
