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

package com.benneighbour.graphqlexample.config;

import com.benneighbour.graphqlexample.config.dataloader.CompanyDataLoader;
import com.benneighbour.graphqlexample.config.dataloader.OfficeDataLoader;
import com.benneighbour.graphqlexample.config.dataloader.RoleDataLoader;
import com.benneighbour.graphqlexample.model.Company;
import com.benneighbour.graphqlexample.model.Office;
import com.benneighbour.graphqlexample.model.Role;
import io.leangen.graphql.spqr.spring.autoconfigure.DataLoaderRegistryFactory;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataLoaderFactory implements DataLoaderRegistryFactory {

  private static final BatchLoader<UUID, Company> companyLoader = CompanyDataLoader::companies;
  private static final BatchLoader<UUID, List<Role>> roleLoader = RoleDataLoader::loadRoles;
  private static final BatchLoader<UUID, List<Office>> officeLoader = OfficeDataLoader::loadOffices;

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
