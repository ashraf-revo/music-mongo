package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Created by ashraf on 18/01/17.
 */
@Document
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @JsonView(ViewDetails.song.class)
    private String id;
    @NotBlank
    @JsonView(ViewDetails.song.class)
    private String title;
    @JsonView(ViewDetails.song.class)
    private String imageUrl = "/assets/images/p1.jpg";
    @JsonView(ViewDetails.song.class)
    private String fileUrl = "/assets/audio/a0.mp3";
    @NotBlank
    @JsonView(ViewDetails.song.class)
    private String description;
    @DBRef
    @NotNull
    @CreatedBy
    @JsonView(ViewDetails.songUser.class)
    private User user;
    @CreatedDate
    @JsonView(ViewDetails.song.class)
    private Date createdDate;
    @DBRef
    @JsonView(ViewDetails.songLikes.class)
    private List<Like> likes = new ArrayList<>();
    @DBRef
    @JsonView(ViewDetails.songViews.class)
    private List<View> views = new ArrayList<>();
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile file;
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile image;
    @Transient
    @JsonView(ViewDetails.song.class)
    private Like liked = null;
}