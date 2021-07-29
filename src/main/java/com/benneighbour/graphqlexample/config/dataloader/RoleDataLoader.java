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

package com.benneighbour.graphqlexample.config.dataloader;

import com.benneighbour.graphqlexample.dao.RoleDao;
import com.benneighbour.graphqlexample.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class RoleDataLoader {

  private static RoleDao roleDao;

  @Autowired
  public RoleDataLoader(RoleDao roleDao) {
    RoleDataLoader.roleDao = roleDao;
  }

  public static CompletableFuture<List<List<Role>>> loadRoles(List<UUID> ids) {
    /* Create a list (with multiple sub-lists) of the same size as the input */
    List<List<Role>> bucketedList =
        new ArrayList<>(Collections.nCopies(ids.size(), new ArrayList<>()));

    /* TODO: Find a better way to batch load this!! */

    /* Batch load all of the companies that contain the given ids */
    roleDao.findAllByEmployeeIdIn(ids).stream()
        /* Convert to Key~Value Pair */
        .flatMap(Collection::stream)
        /* Group the hashmap by the company ids */
        .collect(Collectors.groupingBy(role -> role.getEmployee().getId()))
        /* For each item that maps to an id, group it into the right "bucket" */
        .forEach(
            (uuid, roles) -> {
              bucketedList.set(ids.indexOf(uuid), roles);
            });

    /* Return the ordered list as a completed future */
    return CompletableFuture.completedFuture(bucketedList);
  }
}
