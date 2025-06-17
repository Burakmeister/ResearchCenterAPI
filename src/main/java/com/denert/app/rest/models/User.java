package com.denert.app.rest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String surname;

    @Column(length = 9)
    private int phoneNumber;

    @Column(length = 45)
    private String password;

    @Column(length = 45)
    private String mail;

    @Column(length = 1)
    private int permissions;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-agreements")
    private Set<Agreement> projectsAgreements;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-commissions")
    private Set<TestCommission> projectsCommission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    @JsonBackReference
    private Address address;
}
