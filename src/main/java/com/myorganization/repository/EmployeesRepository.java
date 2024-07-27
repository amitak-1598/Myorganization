package com.myorganization.repository;

import com.myorganization.modelclass.Employees;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.async.AsyncCrudRepository;

@Repository
public interface EmployeesRepository extends AsyncCrudRepository<Employees,Integer> {

}
