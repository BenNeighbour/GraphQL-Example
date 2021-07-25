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

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Office implements Serializable {

  private static final long serialVersionUID = 476906690321337185L;

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid", updatable = false)
  @GraphQLQuery(name = "id", description = "An Office's Id")
  private UUID id;

  @NotNull(message = "You can't be remote!!")
  @GraphQLQuery(name = "officeLocation", description = "Where the office is located")
  private String officeLocation;

  @Min(2)
  @NotNull(message = "I mean come on - everyone has their limits right??")
  @GraphQLQuery(
      name = "maximumHeadcount",
      description = "The maximum number of people the office contains")
  private int maximumHeadcount;

  @NotNull(message = "We all know you dodge your taxes... :D")
  @GraphQLQuery(
          name = "financeType",
          description = "How the company is financing their office")
  private FinanceType financeType;

  @GraphQLIgnore
  @JoinColumn(name = "company_id")
  @ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Company company;

  public enum FinanceType {
    RENTING,
    OWNED,
    LEASING
  }
}
