package vn.com.carrentalsystem.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passwordresettoken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private LocalDateTime expiryDateTime;
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Userz user;

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiryDateTime=" + expiryDateTime +
                ", user=" + user +
                '}';
    }
}