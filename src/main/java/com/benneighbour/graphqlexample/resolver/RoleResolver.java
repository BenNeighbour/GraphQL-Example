package com.benneighbour.graphqlexample.resolver;

import com.benneighbour.graphqlexample.dao.RoleDao;
import com.benneighbour.graphqlexample.model.Employee;
import com.benneighbour.graphqlexample.model.Role;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Validated
@GraphQLApi
@Transactional
@RequiredArgsConstructor
public class RoleResolver {

  private final RoleDao roleDao;

  @GraphQLQuery(name = "roles")
  public CompletableFuture<List<Role>> getRolesInEmployee(
      @GraphQLContext @Valid @GraphQLNonNull Employee employee,
      @GraphQLEnvironment ResolutionEnvironment env) {
    DataLoader<UUID, List<Role>> loader = env.dataFetchingEnvironment.getDataLoader("role");
    return loader.load(employee.getId());
  }
}
