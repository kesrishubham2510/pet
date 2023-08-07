package com.myreflectionthoughts.apipetdetails.repository;

import com.myreflectionthoughts.apipetdetails.entity.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
