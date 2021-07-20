package com.benneighbour.graphqlexample.dao;

import com.benneighbour.graphqlexample.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfficeDao extends JpaRepository<Office, UUID> {

    List<List<Office>> findAllByCompanyIdIn(List<UUID> companyIds);

}
