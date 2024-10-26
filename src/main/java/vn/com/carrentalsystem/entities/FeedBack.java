package vn.com.carrentalsystem.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import vn.com.carrentalsystem.enums.Rate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "feedBack", schema = "dbo")
public class FeedBack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedBack_id")
    private Long feedBackId;

    @Enumerated(EnumType.ORDINAL)
    private Rate rate;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "feedback_date_time")
    private Date feedbackDateTime;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id", unique = true)
    private Booking booking;
}
