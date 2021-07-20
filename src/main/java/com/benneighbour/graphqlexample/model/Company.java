package com.benneighbour.graphqlexample.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {

  private static final long serialVersionUID = -6007975840330441233L;

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid", updatable = false)
  @GraphQLQuery(name = "id", description = "A Company's Id")
  private UUID id;

  @NotNull(message = "There must be a Company Name!")
  @GraphQLQuery(name = "name", description = "A Company's Name")
  private String name;

  @Min(10000)
  @NotNull(message = "You gotta tell us how rich you are!")
  @GraphQLQuery(name = "balance", description = "A Company's Dollar")
  private BigDecimal balance;

  @NotNull(message = "There must be a Company Type!")
  @GraphQLQuery(name = "type", description = "The type of company")
  private CompanyType type;

  @GraphQLQuery(name = "offices", description = "The Company's offices")
  @OneToMany(
      targetEntity = Office.class,
      orphanRemoval = true,
      fetch = FetchType.LAZY,
      mappedBy = "company",
      cascade = CascadeType.ALL)
  private List<Office> offices;

  public enum CompanyType {
    PRIVATE_LIMITED,
    SOLE_TRADER,
    PUBLIC
  }
}
