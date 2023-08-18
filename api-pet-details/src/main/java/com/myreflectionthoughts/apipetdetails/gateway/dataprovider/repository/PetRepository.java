package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
