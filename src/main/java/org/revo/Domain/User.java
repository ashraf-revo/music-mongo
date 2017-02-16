package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Created by ashraf on 18/01/17.
 */
@Document
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("authorities")
public class User extends BaseUser {
    @Id
    @JsonView(ViewDetails.user.class)
    private String id;
    @NotBlank
    @JsonView(ViewDetails.user.class)
    private String name;
    @JsonView(ViewDetails.user.class)
    private String imageUrl = "/assets/images/a0.png";
    @NotBlank
    @JsonView(ViewDetails.user.class)
    private String phone;
    @JsonView(ViewDetails.user.class)
    private String info;
    @Email
    @NotBlank
    @Indexed(unique = true)
    @JsonView(ViewDetails.user.class)
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank
    @JsonView(ViewDetails.user.class)
    private String password;
    @JsonProperty(access = WRITE_ONLY)
    @Transient
    @JsonView(ViewDetails.user.class)
    private String currentPassword;
    @CreatedDate
    @JsonView(ViewDetails.user.class)
    private Date createdDate = new Date();
    @DBRef
    @JsonView(ViewDetails.userSongs.class)
    private List<Song> songs = new ArrayList<>();
    @DBRef
    @JsonView(ViewDetails.userLikes.class)
    private List<Like> likes = new ArrayList<>();
    @DBRef
    @JsonView(ViewDetails.userViews.class)
    private List<View> views = new ArrayList<>();
    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile image;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}