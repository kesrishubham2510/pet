package com.myreflectionthoughts.apipetdetails.gateway.repository;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
