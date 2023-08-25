package com.myreflectionthoughts.apimasterdetails.core.component;

import com.myreflectionthoughts.apimasterdetails.core.usecase.*;
import com.myreflectionthoughts.library.contract.*;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.UpdateMasterDTO;
import com.myreflectionthoughts.library.dto.response.DeleteMasterDTO;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseComponent {

    @Bean
    public CreateMasterUseCase createMasterUseCase(IAdd<AddMasterDTO, MasterDTO> iAdd){return new CreateMasterUseCase(iAdd);}

    @Bean
    public DeleteMasterDetailsUseCase deleteMasterDetailsUseCase(IDelete<DeleteMasterDTO> iDelete){return new DeleteMasterDetailsUseCase(iDelete);}

    @Bean
    public ReadMasterDetailsUseCase readMasterDetailsUseCase(IGet<MasterDTO> iGet){return new ReadMasterDetailsUseCase(iGet);}

    @Bean
    public ReadMastersUseCase readMastersUseCase(IGetAll<MasterDTO> iGetAll){return new ReadMastersUseCase(iGetAll);}

    @Bean
    public UpdateMasterDetailsUseCase updateMasterDetailsUseCase(IUpdate<MasterDTO, UpdateMasterDTO> iUpdate){return new UpdateMasterDetailsUseCase(iUpdate);}
}
