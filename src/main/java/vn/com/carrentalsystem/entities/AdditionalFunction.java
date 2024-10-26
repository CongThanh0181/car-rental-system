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
@Table(name = "additional_function", schema = "dbo")
public class AdditionalFunction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_function_id")
    private Long additionalFunctionId;

    private boolean bluetooth;

    private boolean gps;

    private boolean camera;

    private boolean sunRoof;

    private boolean childLock;

    private boolean childSeat;

    private boolean dvd;

    private boolean usb;

    @OneToOne(mappedBy = "additionalFunction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Car car;

}
