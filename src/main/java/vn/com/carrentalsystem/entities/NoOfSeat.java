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
@Table(name = "no_of_seat", schema = "dbo")
public class NoOfSeat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_of_seat_id")
    private Long noOfSeatId;

    private String noOfSeatName;

}
