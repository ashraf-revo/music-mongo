package org.revo.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.revo.Domain.Song;
import org.revo.Service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ashraf on 09/10/16.
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String saveFile(Song song) {
        try {
            Assert.notNull(song);
            MultipartFile file = song.getFile();
            Assert.notNull(file);
            Assert.isTrue(!file.isEmpty());
            Map map = cloudinary.uploader().uploadLarge(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
            return (String) map.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String saveImage(MultipartFile image) {
        try {
            Assert.notNull(image);
            Assert.isTrue(!image.isEmpty());
            Map map = cloudinary.uploader().uploadLarge(image.getBytes(), ObjectUtils.emptyMap());
            return (String) map.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}