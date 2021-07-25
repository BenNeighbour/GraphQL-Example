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

import com.benneighbour.graphqlexample.dao.EmployeeDao;
import com.benneighbour.graphqlexample.model.Company;
import com.benneighbour.graphqlexample.model.Employee;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Validated
@GraphQLApi
@Transactional
@RequiredArgsConstructor
public class EmployeeResolver {

  private final EmployeeDao employeeDao;

  @GraphQLQuery(name = "getAllEmployees")
  public List<Employee> getAllEmployees() {
    return employeeDao.findAll();
  }

  @GraphQLQuery(name = "getEmployeeById")
  public Optional<Employee> getEmployeeById(
      @GraphQLArgument(name = "id") @GraphQLNonNull @Valid UUID id) {
    return employeeDao.findById(id);
  }

  @GraphQLMutation(name = "saveEmployee")
  public Employee saveEmployee(@GraphQLArgument(name = "employee")
                                 @GraphQLNonNull @Valid Employee company) {
    return employeeDao.save(company);
  }

  @GraphQLMutation(name = "deleteEmployee")
  public void deleteEmployee(@GraphQLArgument(name = "id") @GraphQLNonNull @Valid UUID companyId) {
    employeeDao.deleteById(companyId);
  }

  @GraphQLQuery(name = "company")
  public CompletableFuture<Company> getCompanyById(
          @GraphQLContext @Valid @GraphQLNonNull Employee employee, @GraphQLEnvironment ResolutionEnvironment env) {
    DataLoader<UUID, Company> loader = env.dataFetchingEnvironment.getDataLoader("company");
    return loader.load(employee.getCompany().getId());
  }
}