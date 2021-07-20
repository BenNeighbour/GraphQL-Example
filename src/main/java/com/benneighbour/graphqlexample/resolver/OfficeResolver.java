package com.benneighbour.graphqlexample.resolver;

import com.benneighbour.graphqlexample.dao.OfficeDao;
import com.benneighbour.graphqlexample.model.Company;
import com.benneighbour.graphqlexample.model.Office;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@GraphQLApi
@Transactional
@RequiredArgsConstructor
public class OfficeResolver {

  private final OfficeDao officeDao;

  @GraphQLQuery(name = "offices")
  public CompletableFuture<List<Office>> getOfficesInCompany(
      @GraphQLContext @GraphQLNonNull Company company,
      @GraphQLEnvironment ResolutionEnvironment env) {
    DataLoader<UUID, List<Office>> loader = env.dataFetchingEnvironment.getDataLoader("office");
    return loader.load(company.getId());
  }
}
