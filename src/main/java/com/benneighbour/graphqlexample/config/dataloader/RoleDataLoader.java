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
        List<List<Role>> bucketedList = new ArrayList<>(Collections.nCopies(ids.size(), new ArrayList<>()));

        roleDao.findAllByEmployeeIdIn(ids).stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(role -> role.getEmployee().getId()))
                .forEach((uuid, roles) -> {
                    bucketedList.set(ids.indexOf(uuid), roles);
                });

        return CompletableFuture.completedFuture(bucketedList);
    }

}
