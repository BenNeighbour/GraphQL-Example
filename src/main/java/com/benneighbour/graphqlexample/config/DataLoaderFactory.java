package com.benneighbour.graphqlexample.config;

import com.benneighbour.graphqlexample.dao.CompanyDao;
import com.benneighbour.graphqlexample.dao.RoleDao;
import com.benneighbour.graphqlexample.model.Company;
import com.benneighbour.graphqlexample.model.Role;
import io.leangen.graphql.spqr.spring.autoconfigure.DataLoaderRegistryFactory;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class DataLoaderFactory implements DataLoaderRegistryFactory {

  private static CompanyDao companyDao;
  private static final BatchLoader<UUID, Company> companyLoader = DataLoaderFactory::companies;
  private static RoleDao roleDao;
  private static final BatchLoader<UUID, List<Role>> roleLoader = DataLoaderFactory::roles;

  @Autowired
  public DataLoaderFactory(CompanyDao companyDao, RoleDao roleDao) {
    DataLoaderFactory.companyDao = companyDao;
    DataLoaderFactory.roleDao = roleDao;
  }

  public static CompletableFuture<List<Company>> companies(List<UUID> ids) {
    return CompletableFuture.completedFuture(companyDao.findAllById(ids));
  }

  public static CompletableFuture<List<List<Role>>> roles(List<UUID> ids) {
    return CompletableFuture.completedFuture(roleDao.findAllByEmployeeIdIn(ids));
  }

  @Override
  @Bean
  public DataLoaderRegistry createDataLoaderRegistry() {
    DataLoaderRegistry loaders = new DataLoaderRegistry();
    loaders.register("company", new DataLoader<>(companyLoader));
    loaders.register("role", new DataLoader<>(roleLoader));

    return loaders;
  }
}
