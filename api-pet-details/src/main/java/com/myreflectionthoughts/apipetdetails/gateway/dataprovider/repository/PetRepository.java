package com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository;

import com.myreflectionthoughts.apipetdetails.core.entity.Pet;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PetRepository extends ReactiveMongoRepository<Pet, String> {

    public Flux<PetDTO> findAllByMaster(String masterId);
}
