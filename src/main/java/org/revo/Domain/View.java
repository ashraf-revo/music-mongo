package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.revo.Util.ViewDetails;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ashraf on 18/01/17.
 */
@Document
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class View {
    @Id
    @JsonView(ViewDetails.view.class)
    private String id;
    @DBRef
    @NotNull
    @CreatedBy
    @JsonView(ViewDetails.viewUser.class)
    private User user;
    @DBRef
    @NotNull
    @JsonView(ViewDetails.viewSong.class)
    private Song song;
    @CreatedDate
    @JsonView(ViewDetails.view.class)
    private Date createdDate;
}