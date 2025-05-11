package org.example.autoriaclone.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.autoriaclone.view.Views;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Data
@NoArgsConstructor
@Entity
@EnableAutoConfiguration
@ToString
@Table(name = "images", schema = "public")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private Integer id;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    private String imageName;
    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonView({Views.Details.class})
    private byte[] data;
    public Image(String imageName){
        this.imageName = imageName;
    }
}
