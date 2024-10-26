package vn.com.carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "brand_car", schema = "dbo")
public class BrandCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_car_id")
    private Long brandCarId;

    @Column(name = "brand_name")
    private String brandName;

    @JsonManagedReference
    @OneToMany(mappedBy = "brandCar")
    private List<ModelCar> modelCarList;

}
