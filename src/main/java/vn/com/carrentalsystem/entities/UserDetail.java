package vn.com.carrentalsystem.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_detail", schema = "dbo")
public class UserDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long userDetailId;

    @Column(name = "national_id_no", unique = true)
    private String nationalIdNo;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String phone;

    private String driverName;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "driving_license_id", referencedColumnName = "driving_license_id", unique = true)
    private DrivingLicense drivingLicense;

    @OneToOne(mappedBy = "userDetail")
    private Userz userz;

    @OneToMany(mappedBy = "userDetail")
    private List<Booking> bookingList;

}
