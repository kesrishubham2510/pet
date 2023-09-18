package com.myreflectionthoughts.apipetdetails.gateway.dataprovider;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.exception.PetNotFoundException;
import com.myreflectionthoughts.apipetdetails.core.utils.LogUtility;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.repository.PetRepository;
import com.myreflectionthoughts.apipetdetails.gateway.dataprovider.utility.MappingUtility;
import com.myreflectionthoughts.library.contract.IDelete;
import com.myreflectionthoughts.library.dto.response.DeletePetDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DeletePetDataProvider extends DataProvider implements IDelete<DeletePetDTO> {

    private final Logger logger;

    public DeletePetDataProvider(PetRepository petRepository, MappingUtility mappingUtility){
        this.mappingUtility = mappingUtility;
        this.petRepository = petRepository;
        logger = Logger.getLogger(DeletePetDataProvider.class.getName());
    }

    @Override
    public Mono<DeletePetDTO> delete(Mono<String> petIdMono) {
        return  petIdMono
                .filterWhen(petRepository::existsById)
                .doOnNext(petId-> LogUtility.loggerUtility.log(logger, "Initiating pet:- "+petId+" deletion...", Level.INFO))
                .map(this::handleDeletion)
                .switchIfEmpty(Mono.error(new PetNotFoundException((ServiceConstants.PET_NOT_FOUND_EXCEPTION))));
    }

    private DeletePetDTO handleDeletion(String petId){
        // since reactive pipeline runs only when subscribe
        Consumer<Void> consumer = (v)-> LogUtility.loggerUtility.log(logger, "Pet "+petId+"deleted successfully..", Level.INFO);
        petRepository.deleteById(petId).subscribe(consumer);
        return mappingUtility.createDeletePetDTO(petId);
    }
}