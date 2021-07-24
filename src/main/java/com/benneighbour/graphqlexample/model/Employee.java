package com.benneighbour.graphqlexample.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

  @ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @GraphQLQuery(name = "company", description = "The Company a Person works for")
  private Company company;

  @GraphQLQuery(name = "roles", description = "The Role a Person plays in their Company")
  @OneToMany(
      targetEntity = Role.class,
      orphanRemoval = true,
      fetch = FetchType.LAZY,
      mappedBy = "employee",
      cascade = CascadeType.ALL)
  private List<Role> roles = new ArrayList<>();

  public enum Vehicle {
    CAR,
    BUS,
    VAN,
    BICYCLE,
    MOTORBIKE,
    SCOOTER
  }
}
