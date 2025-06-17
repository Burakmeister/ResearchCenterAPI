package com.denert.app.rest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @OneToMany(mappedBy = "address")
    @JsonManagedReference
    private Set<User> users;

    @Column(length = 45)
    private String city;

    @Column(length = 45)
    private String streetName;

    @Column(length = 6)
    private String houseNumber;

    @Column(length = 5)
    private int postcode;

    @Column(length = 4)
    private int flatNumber;

}
