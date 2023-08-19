package com.myreflectionthoughts.apimasterdetails.gateway.dataprovider.repository;

import com.myreflectionthoughts.apimasterdetails.core.entity.Master;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends ReactiveMongoRepository<Master, String>{
}
