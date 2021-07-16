package com.benneighbour.graphqlexample.resolver;

import com.benneighbour.graphqlexample.dao.EmployeeDao;
import com.benneighbour.graphqlexample.model.Employee;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class EmployeeResolver {

    private final EmployeeDao employeeDao;

    @GraphQLQuery(name = "getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    @GraphQLQuery(name = "getEmployeeById")
    public Optional<Employee> getEmployeeById(@GraphQLArgument(name = "id") UUID id) {
        return employeeDao.findById(id);
    }

    @GraphQLMutation(name = "saveEmployee")
    public Employee saveEmployee(@GraphQLArgument(name = "employee") Employee company) {
        return employeeDao.save(company);
    }

    @GraphQLMutation(name = "deleteEmployee")
    public void deleteEmployee(@GraphQLArgument(name = "id") UUID companyId) {
        employeeDao.deleteById(companyId);
    }

}
