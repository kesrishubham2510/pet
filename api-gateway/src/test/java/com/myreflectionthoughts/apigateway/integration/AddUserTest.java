package com.myreflectionthoughts.apigateway.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.myreflectionthoughts.apigateway.core.constant.ServiceConstant;
import com.myreflectionthoughts.library.dto.request.AddMasterDTO;
import com.myreflectionthoughts.library.dto.request.AddPetDTO;
import com.myreflectionthoughts.library.dto.request.AddUserDTO;
import com.myreflectionthoughts.library.dto.response.ExceptionResponse;
import com.myreflectionthoughts.library.dto.response.MasterDTO;
import com.myreflectionthoughts.library.dto.response.PetDTO;
import com.myreflectionthoughts.library.dto.response.UserDTO;
import com.myreflectionthoughts.library.exception.InputDataException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddUserTest extends TestSetup{

     @Test
     void testAddUser(){

         wireMockServer.stubFor(
                 WireMock
                 .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                 .willReturn(WireMock.aResponse()
                 .withStatus(201)
                 .withHeader("Content-Type","application/json")
                 .withBodyFile("AddMaster_Success.json"))
         );

         wireMockServer.stubFor(
                 WireMock
                 .post(String.format("%s/add",ServiceConstant.petServiceAPIQualifier))
                 .willReturn(WireMock.aResponse()
                 .withStatus(201)
                 .withHeader("Content-Type","application/json")
                 .withBodyFile("AddPet_Success.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());

         MasterDTO expectedMasterResponse = generateMasterDTO();
         PetDTO expectedPetResponse = generatePetDTO();

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isCreated()
                 .expectBody(UserDTO.class)
                 .consumeWith(registerUserResponse->{
                    assertEquals(expectedMasterResponse.getName(), registerUserResponse.getResponseBody().getMaster().getName());
                    assertEquals(expectedMasterResponse.getEmail(), registerUserResponse.getResponseBody().getMaster().getEmail());
                    assertEquals(expectedMasterResponse.getAge(), registerUserResponse.getResponseBody().getMaster().getAge());
                    assertEquals(expectedMasterResponse.getAddress(), registerUserResponse.getResponseBody().getMaster().getAddress());
                    assertEquals(expectedMasterResponse.isMarkForDelete(), registerUserResponse.getResponseBody().getMaster().isMarkForDelete());

                    assertEquals(expectedPetResponse.getMaster(), registerUserResponse.getResponseBody().getPets().get(0).getMaster());
                    assertEquals(expectedPetResponse.getName(), registerUserResponse.getResponseBody().getPets().get(0).getName());
                    assertEquals(expectedPetResponse.getCategory(), registerUserResponse.getResponseBody().getPets().get(0).getCategory());
                    assertEquals(expectedPetResponse.getGender(), registerUserResponse.getResponseBody().getPets().get(0).getGender());
                    assertEquals(expectedPetResponse.getClinicCardStatus(), registerUserResponse.getResponseBody().getPets().get(0).getClinicCardStatus());
                    assertEquals(expectedPetResponse.getClinicCardStatusMessage(), registerUserResponse.getResponseBody().getPets().get(0).getClinicCardStatusMessage());
                    assertEquals(expectedPetResponse.getAge(), registerUserResponse.getResponseBody().getPets().get(0).getAge());
                 });
     }

     @Test
     void testAddUser_Throws_InputDataException_Name(){

         wireMockServer.stubFor(
                 WireMock
                         .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                         .willReturn(WireMock.aResponse()
                                 .withStatus(400)
                                 .withHeader("Content-Type","application/json")
                                 .withBodyFile("AddMaster_IDEx_Name.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());
         userDTO.getMaster().setName("");

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody(ExceptionResponse.class)
                 .consumeWith(registerUserExceptionResponse->{
                     assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                     assertEquals("name is required, it can't null or empty or whitespaces", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                 });
     }
     @Test
     void testAddUser_Throws_InputDataException_Email(){

         wireMockServer.stubFor(
                 WireMock
                         .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                         .willReturn(WireMock.aResponse()
                                 .withStatus(400)
                                 .withHeader("Content-Type","application/json")
                                 .withBodyFile("AddMaster_IDEx_Email.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());
         userDTO.getMaster().setEmail("abcd@domain.com");

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody(ExceptionResponse.class)
                 .consumeWith(registerUserExceptionResponse->{
                     assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                     assertEquals("Email must contain one of these domains [gmail.com, facebook.com, yopmail.com]", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                 });
     }
     @Test
     void testAddUser_Throws_InputDataException_Password(){

         wireMockServer.stubFor(
                 WireMock
                         .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                         .willReturn(WireMock.aResponse()
                                 .withStatus(400)
                                 .withHeader("Content-Type","application/json")
                                 .withBodyFile("AddMaster_IDEx_Password.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());
         userDTO.getMaster().setPassword("");

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody(ExceptionResponse.class)
                 .consumeWith(registerUserExceptionResponse->{
                     assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                     assertEquals("Password is required, it can't null or empty or whitespaces", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                 });
     }

    @Test
    void testAddUser_Throws_InputDataException_PasswordLength(){

        wireMockServer.stubFor(
                WireMock
                        .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                        .willReturn(WireMock.aResponse()
                                .withStatus(400)
                                .withHeader("Content-Type","application/json")
                                .withBodyFile("AddMaster_IDEx_PasswordLength.json"))
        );

        AddUserDTO userDTO = new AddUserDTO();
        userDTO.setMaster(generateAddMasterDTO());
        userDTO.setPets(generateAddPetList());
        userDTO.getMaster().setPassword("jhbj");

        webTestClient.post()
                .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                .bodyValue(userDTO)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ExceptionResponse.class)
                .consumeWith(registerUserExceptionResponse->{
                    assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                    assertEquals("Password should be atleast 6 characters long", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                });
    }

     @Test
     void testAddUser_Throws_InputDataException_Age(){

         wireMockServer.stubFor(
                 WireMock
                         .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                         .willReturn(WireMock.aResponse()
                                 .withStatus(400)
                                 .withHeader("Content-Type","application/json")
                                 .withBodyFile("AddMaster_IDEx_Age.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());
         userDTO.getMaster().setAge(5);

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody(ExceptionResponse.class)
                 .consumeWith(registerUserExceptionResponse->{
                     assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                     assertEquals("Please provide a Valid age should be atleast 16 years old", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                 });
     }
     @Test
     void testAddUser_Throws_InputDataException_Address(){

         wireMockServer.stubFor(
                 WireMock
                         .post(String.format("%s/add", ServiceConstant.masterServiceAPIQualifier))
                         .willReturn(WireMock.aResponse()
                                 .withStatus(400)
                                 .withHeader("Content-Type","application/json")
                                 .withBodyFile("AddMaster_IDEx_Address.json"))
         );

         AddUserDTO userDTO = new AddUserDTO();
         userDTO.setMaster(generateAddMasterDTO());
         userDTO.setPets(generateAddPetList());
         userDTO.getMaster().setAddress(null);

         webTestClient.post()
                 .uri(String.format("%s/register",ServiceConstant.API_QUALIFIER))
                 .bodyValue(userDTO)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody(ExceptionResponse.class)
                 .consumeWith(registerUserExceptionResponse->{
                     assertEquals(InputDataException.class.getSimpleName(), registerUserExceptionResponse.getResponseBody().getError());
                     assertEquals("address is required, it can't null or empty or whitespaces", registerUserExceptionResponse.getResponseBody().getErrorMessage());
                 });
     }



    private AddMasterDTO generateAddMasterDTO(){
        AddMasterDTO addMasterDTO = new AddMasterDTO();
        addMasterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        addMasterDTO.setEmail(ServiceConstant.VALID_MASTER_EMAIL);
        addMasterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        addMasterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        addMasterDTO.setPassword(ServiceConstant.VALID_MASTER_PASSWORD);
        return addMasterDTO;
    }

    private MasterDTO generateMasterDTO(){
        MasterDTO masterDTO = new MasterDTO();
        masterDTO.setId(ServiceConstant.VALID_MASTER_ID);
        masterDTO.setName(ServiceConstant.VALID_MASTER_NAME);
        masterDTO.setEmail(ServiceConstant.VALID_MASTER_EMAIL);
        masterDTO.setAddress(ServiceConstant.VALID_MASTER_ADDRESS);
        masterDTO.setAge(ServiceConstant.VALID_MASTER_AGE);
        return masterDTO;
    }

    private PetDTO generatePetDTO() {
        PetDTO pet = new PetDTO();
        pet.setMaster(ServiceConstant.VALID_MASTER_ID);
        pet.setId(ServiceConstant.DUMMY_PET_ID);
        pet.setAge(1.0);
        pet.setCategory("DOG");
        pet.setGender("FEMALE");
        pet.setName("pet-name");
        pet.setClinicCardStatus("NOT_APPLIED");
        pet.setClinicCardStatusMessage("User has not applied for the Card yet");
        return pet;
    }

    private List<AddPetDTO> generateAddPetList(){
        return List.of(getAddPetDTO());
    }

    private AddPetDTO getAddPetDTO() {
        AddPetDTO addPetDTO = new AddPetDTO();
        addPetDTO.setMaster("");
        addPetDTO.setAge(1.0);
        addPetDTO.setCategory("dog");
        addPetDTO.setGender("female");
        addPetDTO.setName("pet-name");
        return addPetDTO;
    }
}

