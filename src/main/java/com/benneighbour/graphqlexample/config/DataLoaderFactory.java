package com.benneighbour.graphqlexample.config;

import com.benneighbour.graphqlexample.dao.CompanyDao;
import com.benneighbour.graphqlexample.dao.OfficeDao;
import com.benneighbour.graphqlexample.dao.RoleDao;
import com.benneighbour.graphqlexample.model.Company;
import com.benneighbour.graphqlexample.model.Office;
import com.benneighbour.graphqlexample.model.Role;
import io.leangen.graphql.spqr.spring.autoconfigure.DataLoaderRegistryFactory;
import org.apache.commons.collections4.list.FixedSizeList;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class DataLoaderFactory implements DataLoaderRegistryFactory {

  private static CompanyDao companyDao;
  private static final BatchLoader<UUID, Company> companyLoader = DataLoaderFactory::companies;
  private static RoleDao roleDao;
  private static final BatchLoader<UUID, List<Role>> roleLoader = DataLoaderFactory::roles;
  private static OfficeDao officeDao;
  private static final BatchLoader<UUID, List<Office>> officeLoader = DataLoaderFactory::offices;

  @Autowired
  public DataLoaderFactory(CompanyDao companyDao, RoleDao roleDao, OfficeDao officeDao) {
    DataLoaderFactory.companyDao = companyDao;
    DataLoaderFactory.roleDao = roleDao;
    DataLoaderFactory.officeDao = officeDao;
  }

  public static CompletableFuture<List<Company>> companies(List<UUID> ids) {
    return CompletableFuture.completedFuture(companyDao.findAllById(ids));
  }

  public static CompletableFuture<List<List<Role>>> roles(List<UUID> ids) {
    List<List<Role>> bucketedList = new ArrayList<>(Collections.nCopies(ids.size(), new ArrayList<>()));

    roleDao.findAllByEmployeeIdIn(ids).stream()
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(role -> role.getEmployee().getId()))
            .forEach((uuid, roles) -> {
              bucketedList.set(ids.indexOf(uuid), roles);
            });

    return CompletableFuture.completedFuture(bucketedList);
  }

  public static CompletableFuture<List<List<Office>>> offices(List<UUID> ids) {
    List<List<Office>> bucketedList = new ArrayList<>(Collections.nCopies(ids.size(), new ArrayList<>()));

    officeDao.findAllByCompanyIdIn(ids).stream()
        .flatMap(Collection::stream)
        .collect(Collectors.groupingBy(office -> office.getCompany().getId()))
        .forEach((uuid, offices) -> {
          bucketedList.set(ids.indexOf(uuid), offices);
        });

    return CompletableFuture.completedFuture(bucketedList);
  }

  @Override
  @Bean
  public DataLoaderRegistry createDataLoaderRegistry() {
    DataLoaderRegistry loaders = new DataLoaderRegistry();
    loaders.register("company", new DataLoader<>(companyLoader));
    loaders.register("role", new DataLoader<>(roleLoader));
    loaders.register("office", new DataLoader<>(officeLoader));

    return loaders;
  }
}
