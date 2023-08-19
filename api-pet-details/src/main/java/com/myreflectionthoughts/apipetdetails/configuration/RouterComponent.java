package com.myreflectionthoughts.apipetdetails.configuration;

import com.myreflectionthoughts.apipetdetails.gateway.routers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouterComponent {

    @Bean
    public CreatePetRequestRouter createPetRequestRouter(){return new CreatePetRequestRouter();}
    @Bean
    public DeletePetRequestRouter deletePetRequestRouter(){return new DeletePetRequestRouter();}
    @Bean
    public GetPetRequestRouter getPetRequestRouter(){return new GetPetRequestRouter();}
    @Bean
    public GetPetsRequestRouter getPetsRequestRouter(){return new GetPetsRequestRouter();}
    @Bean
    public UpdatePetRequestRouter updatePetRequestRouter(){return new UpdatePetRequestRouter();}
}
