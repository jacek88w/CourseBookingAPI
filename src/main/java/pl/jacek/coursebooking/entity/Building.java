package pl.jacek.coursebooking.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.jacek.coursebooking.model.Address;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Building {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @Embedded
    private Address address;
}
