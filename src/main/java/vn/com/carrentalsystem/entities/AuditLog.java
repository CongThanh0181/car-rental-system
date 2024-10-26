package vn.com.carrentalsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "last_visited_date_time")
    private LocalDateTime lastVisitedDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Userz userz;
}
