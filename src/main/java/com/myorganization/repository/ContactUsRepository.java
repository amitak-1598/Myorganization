package com.myorganization.repository;

import com.myorganization.model.amitedge.ContactUs;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.async.AsyncCrudRepository;

@Repository
public interface ContactUsRepository extends AsyncCrudRepository<ContactUs, Integer> {

}
