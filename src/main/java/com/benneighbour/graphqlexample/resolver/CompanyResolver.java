package com.benneighbour.graphqlexample.resolver;

import com.benneighbour.graphqlexample.dao.CompanyDao;
import com.benneighbour.graphqlexample.model.Company;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class CompanyResolver {

    private final CompanyDao companyDao;

    @GraphQLQuery(name = "getAllCompanies")
    public List<Company> getAllCompanies() {
        return companyDao.findAll();
    }

    @GraphQLQuery(name = "getCompanyById")
    public Optional<Company> getCompanyId(@GraphQLArgument(name = "id") UUID id) {
        return companyDao.findById(id);
    }

    @GraphQLMutation(name = "saveCompany")
    public Company saveCompany(@GraphQLArgument(name = "company") Company company) {
        return companyDao.save(company);
    }

    @GraphQLMutation(name = "deleteCompany")
    public void deleteCompany(@GraphQLArgument(name = "id") UUID companyId) {
        companyDao.deleteById(companyId);
    }

    @GraphQLQuery(name = "isRich")
    public boolean isRich(@GraphQLContext Company company) {
        return company.getBalance().compareTo(new BigDecimal(10000000)) < 0;
    }
}
