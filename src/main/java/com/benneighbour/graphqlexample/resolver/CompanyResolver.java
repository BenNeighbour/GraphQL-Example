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

import com.benneighbour.graphqlexample.dao.CompanyDao;
import com.benneighbour.graphqlexample.model.Company;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@GraphQLApi
@Transactional
@RequiredArgsConstructor
public class CompanyResolver {

  private final CompanyDao companyDao;

  @GraphQLQuery(name = "getAllCompanies")
  public List<Company> getAllCompanies() {
    return companyDao.findAll();
  }

  @GraphQLMutation(name = "saveCompany")
  public Company saveCompany(
      @GraphQLArgument(name = "company") @GraphQLNonNull @Valid Company company) {
    return companyDao.save(company);
  }

  @GraphQLMutation(name = "deleteCompany")
  public void deleteCompany(@GraphQLArgument(name = "id") @GraphQLNonNull @Valid UUID companyId) {
    companyDao.deleteById(companyId);
  }

  @GraphQLQuery(name = "isRich")
  public boolean isRich(@GraphQLContext Company company) {
    return company.getBalance().compareTo(new BigDecimal(10000000)) < 0;
  }
}
