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

import com.benneighbour.graphqlexample.dao.OfficeDao;
import com.benneighbour.graphqlexample.model.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class OfficeDataLoader {

    private static OfficeDao officeDao;

    @Autowired
    public OfficeDataLoader(OfficeDao officeDao) {
        OfficeDataLoader.officeDao = officeDao;
    }

    public static CompletableFuture<List<List<Office>>> loadOffices(List<UUID> ids) {
        List<List<Office>> bucketedList = new ArrayList<>(Collections.nCopies(ids.size(), new ArrayList<>()));

        officeDao.findAllByCompanyIdIn(ids).stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(office -> office.getCompany().getId()))
                .forEach((uuid, offices) -> {
                    bucketedList.set(ids.indexOf(uuid), offices);
                });

        return CompletableFuture.completedFuture(bucketedList);
    }

}
