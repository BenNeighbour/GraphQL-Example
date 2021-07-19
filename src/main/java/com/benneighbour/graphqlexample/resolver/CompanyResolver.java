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
