package com.myreflectionthoughts.apipetdetails.gateway.entrypoint;

import com.myreflectionthoughts.apipetdetails.core.constant.ServiceConstants;
import com.myreflectionthoughts.apipetdetails.core.utility.MappingUtility;
import com.myreflectionthoughts.apipetdetails.gateway.service.PetService;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ServiceConstants.API_QUALIFIER)
public class GetAllPetsEndpoint{

    @Autowired
    private MappingUtility mappingUtility;

    @Autowired
    private PetService petService;

    @GetMapping("/get/all")
    public Flux<PetDTO> getPets(){
        return petService.getAll();
    }
}
