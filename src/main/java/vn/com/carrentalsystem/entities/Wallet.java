package vn.com.carrentalsystem.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet", schema = "dbo")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long walletId;

    @Column(name = "balance", columnDefinition = "MONEY")
    private Double balance;

    @OneToOne(mappedBy = "wallet")
    private Userz userz;

    @OneToMany(mappedBy = "wallet")
    private List<PaymentTransaction> paymentTransactions;
}
