package com.myreflectionthoughts.apimasterdetails.configuration;

import com.myreflectionthoughts.apimasterdetails.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseComponent {

    @Bean
    public CreateMasterUseCase createMasterUseCase(){return new CreateMasterUseCase();}

    @Bean
    public DeleteMasterDetailsUseCase deleteMasterDetailsUseCase(){return new DeleteMasterDetailsUseCase();}

    @Bean
    public ReadMasterDetailsUseCase readMasterDetailsUseCase(){return new ReadMasterDetailsUseCase();}

    @Bean
    public ReadMastersUseCase readMastersUseCase(){return new ReadMastersUseCase();}

    @Bean
    public UpdateMasterDetailsUseCase updateMasterDetailsUseCase(){return new UpdateMasterDetailsUseCase();}
}
