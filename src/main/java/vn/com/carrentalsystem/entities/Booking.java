package vn.com.carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import vn.com.carrentalsystem.enums.BookingStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "booking", schema = "dbo")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "booking_no")
    private String bookingNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Userz userz;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private FeedBack feedBack;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private Payment payment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_detail_id", referencedColumnName = "user_detail_id")
    private UserDetail userDetail;

}
