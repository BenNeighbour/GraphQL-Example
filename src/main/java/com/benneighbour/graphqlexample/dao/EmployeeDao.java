package com.benneighbour.graphqlexample.dao;

import com.benneighbour.graphqlexample.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, UUID> {}
