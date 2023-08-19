package com.myreflectionthoughts.apipetdetails.configuration;

import com.myreflectionthoughts.apipetdetails.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseComponent {
    @Bean
    CreatePetUseCase createPetUsecase(){ return  new CreatePetUseCase();}
    @Bean
    ReadPetDetailsUseCase readPetDetailsUseCase(){ return  new ReadPetDetailsUseCase();}
    @Bean
    ReadPetsUseCase readPetsUseCase(){ return  new ReadPetsUseCase();}
    @Bean
    UpdatePetDetailsUseCase updatePetDetailsUseCase(){ return  new UpdatePetDetailsUseCase();}
    @Bean
    DeletePetDetailsUseCase deletePetDetailsUseCase(){ return  new DeletePetDetailsUseCase();}
}
