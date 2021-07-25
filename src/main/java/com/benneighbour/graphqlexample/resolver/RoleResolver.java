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
