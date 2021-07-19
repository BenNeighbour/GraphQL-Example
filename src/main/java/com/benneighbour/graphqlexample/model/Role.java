package com.benneighbour.graphqlexample.model;

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 7935627001099653596L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @GraphQLQuery(name = "id", description = "A Role's Id")
    private UUID id;

    @NotNull(message = "The role must have a name")
    @GraphQLQuery(name = "roleName", description = "The Name of someone's role")
    private String roleName;

    @GraphQLQuery(name = "description", description = "A description of someone's role")
    private String description;

    @GraphQLIgnore
    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;

}
