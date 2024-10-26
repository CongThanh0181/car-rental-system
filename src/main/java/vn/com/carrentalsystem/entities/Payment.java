package vn.com.carrentalsystem.entities;

import lombok.*;
import vn.com.carrentalsystem.enums.PaymentMethod;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment", schema = "dbo")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(18,2)")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id", unique = true)
    private Booking booking;

    @OneToMany(mappedBy = "payment")
    private List<PaymentTransaction> paymentTransactions;
}
