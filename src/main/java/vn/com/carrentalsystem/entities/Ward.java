package vn.com.carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ward", schema = "dbo")
public class Ward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "ward_name")
    private String wardName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;
}
