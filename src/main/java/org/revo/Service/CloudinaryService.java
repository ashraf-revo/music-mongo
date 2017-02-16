package org.revo.Service;

import org.revo.Domain.Song;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ashraf on 09/10/16.
 */
public interface CloudinaryService {
    String saveFile(Song song);

    String saveImage(MultipartFile file);
}