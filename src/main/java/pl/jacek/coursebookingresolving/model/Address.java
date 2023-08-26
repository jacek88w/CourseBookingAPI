package pl.jacek.coursebookingresolving.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    public String street;
    public String city;
    public String zipCode;
    public Integer streetNumber;
}
