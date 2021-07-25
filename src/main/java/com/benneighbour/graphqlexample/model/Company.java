/*
 * Copyright 2021 Ben Neighbour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
