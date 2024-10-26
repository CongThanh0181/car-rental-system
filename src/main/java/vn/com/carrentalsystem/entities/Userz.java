package vn.com.carrentalsystem.entities;

import lombok.*;
import vn.com.carrentalsystem.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "userz", schema = "dbo")
public class Userz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "user_detail_id", referencedColumnName = "user_detail_id", unique = true)
    private UserDetail userDetail;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id", unique = true)
    private Wallet wallet;

    @OneToMany(mappedBy = "userz")
    private List<Car> carList;

    @OneToMany(mappedBy = "userz")
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "userz")
    private List<AuditLog> auditLogList;

    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    @Override
    public String toString() {
        return "Userz{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userDetail=" + userDetail +
                ", wallet=" + wallet +
                '}';
    }
}
