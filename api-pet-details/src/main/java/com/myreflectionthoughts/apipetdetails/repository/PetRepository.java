package com.myreflectionthoughts.apipetdetails.repository;

import com.myreflectionthoughts.apipetdetails.entity.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
}
