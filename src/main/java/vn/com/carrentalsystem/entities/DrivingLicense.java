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
@Table(name = "driving_license", schema = "dbo")
public class DrivingLicense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driving_license_id")
    private Long drivingLicenseId;

    @Column(name = "url_image")
    private String urlImage;

    @OneToOne(mappedBy = "drivingLicense")
    private UserDetail userDetail;
}
