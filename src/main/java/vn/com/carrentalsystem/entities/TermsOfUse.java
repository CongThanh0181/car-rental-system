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
@Table(name = "terms_of_use", schema = "dbo")
public class TermsOfUse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_of_use_id")
    private Long termsOfUseId;

    @Column(name = "no_smoking")
    private boolean noSmoking;

    @Column(name = "no_pet")
    private boolean noPet;

    @Column(name = "no_food_in_car")
    private boolean noFoodInCar;

    private boolean other;

    @Column(name = "description_of_other")
    private String descriptionOfOther;

    @OneToOne(mappedBy = "termsOfUse", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Car car;
}
