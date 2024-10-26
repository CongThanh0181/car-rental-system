package vn.com.carrentalsystem.entities;

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
@Table(name = "address", schema = "dbo")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    private String city;

    private String district;

    private String ward;

    @Column(name = "streest_home_number")
    private String streetHomeNumber;

    @OneToMany(mappedBy = "address")
    private List<UserDetail> userDetailList;

    @OneToMany(mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Car> carList;
}
