package vn.com.carrentalsystem.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "image_car", schema = "dbo")
public class ImageCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_car_id")
    private Long imageCarId;

    @Column(name = "url_image_front")
    private String urlImageFront;

    @Column(name = "url_image_back")
    private String urlImageBack;

    @Column(name = "url_image_right")
    private String urlImageRight;

    @Column(name = "url_image_left")
    private String urlImageLeft;

    @OneToOne(mappedBy = "imageCar", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Car car;
}
