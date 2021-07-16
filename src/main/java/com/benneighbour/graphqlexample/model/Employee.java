package com.benneighbour.graphqlexample.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 684733882540759135L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @GraphQLQuery(name = "id", description = "A Person's Id")
    private UUID id;

    @NotNull(message = "There must be a Person's Name!")
    @GraphQLQuery(name = "fullName", description = "A Person's Name")
    private String fullName;

    @NotNull(message = "A Person must have an Age!")
    @GraphQLQuery(name = "age", description = "A Person's Age")
    private int age;

    @NotNull(message = "A Person must have a Vehicle!")
    @GraphQLQuery(name = "personalVehicle", description = "A Person's Mode of Transport")
    private Vehicle personalVehicle;

    public enum Vehicle {
        CAR,
        BUS,
        VAN,
        BICYCLE,
        MOTORBIKE,
        SCOOTER
    }

}
