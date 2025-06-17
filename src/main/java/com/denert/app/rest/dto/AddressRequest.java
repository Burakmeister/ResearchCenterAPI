package com.denert.app.rest.dto;

import com.denert.app.rest.models.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {


    private Set<User> users;

    private String city;

    private String streetName;

    private String houseNumber;

    private int postcode;

    private int flatNumber;

}
