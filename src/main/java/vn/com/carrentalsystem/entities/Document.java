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
@Table(name = "document", schema = "dbo")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "registration")
    private String registration;

    @Column(name = "certificate")
    private String certificate;

    @Column(name = "insurance")
    private String insurance;

    @OneToOne(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Car car;

}
