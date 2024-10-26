package vn.com.carrentalsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import vn.com.carrentalsystem.enums.CarStatus;
import vn.com.carrentalsystem.enums.FuelType;
import vn.com.carrentalsystem.enums.TransmissionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car", schema = "dbo")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    private String brand;

    private String model;

    private String color;

    @Column(name = "production_year")
    private Integer productionYear;

    @Column(name = "number_of_seat")
    private Integer numberOfSeat;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    private Integer mileage;

    @Column(name = "fuel_consumption")
    private Double fuelConsumption;

    @Column(name = "base_price", columnDefinition = "DECIMAL(18,2)")
    private Double basePrice;

    @Column(name = "deposit", columnDefinition = "DECIMAL(18,2)")
    private Double deposit;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "car_status")
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "additional_function_id", referencedColumnName = "additional_function_id", unique = true)
    private AdditionalFunction additionalFunction;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "terms_of_use_id", referencedColumnName = "terms_of_use_id", unique = true)
    private TermsOfUse termsOfUse;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "image_car_id", referencedColumnName = "image_car_id", unique = true)
    private ImageCar imageCar;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "document_id", unique = true)
    private Document document;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Userz userz;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private List<Booking> bookingList;

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", productionYear=" + productionYear +
                '}';
    }
}
