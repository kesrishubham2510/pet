package com.myreflectionthoughts.apimasterdetails.repository;

import com.myreflectionthoughts.apimasterdetails.entity.Master;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends ReactiveMongoRepository<Master, String>{
}
