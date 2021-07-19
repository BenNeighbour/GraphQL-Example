package com.benneighbour.graphqlexample.dao;

import com.benneighbour.graphqlexample.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleDao extends JpaRepository<Role, UUID> {

    List<List<Role>> findAllByEmployeeIdIn(List<UUID> employeeIds);

}
