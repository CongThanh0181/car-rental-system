package vn.com.carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "model_car", schema = "dbo")
public class ModelCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_car_id")
    private Long modelCarId;

    @Column(name = "model_name")
    private String modelName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "brand_car_id", referencedColumnName = "brand_car_id")
    private BrandCar brandCar;


}
