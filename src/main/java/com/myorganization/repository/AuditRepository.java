package com.myorganization.repository;

import com.myorganization.modelclass.Audit;


import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.async.AsyncCrudRepository;

@Repository
public interface AuditRepository extends AsyncCrudRepository<Audit,Integer>{
	
	

}
