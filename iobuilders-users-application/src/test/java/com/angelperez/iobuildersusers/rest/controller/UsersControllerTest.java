package com.angelperez.iobuildersusers.rest.controller;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.model.User;
import com.angelperez.iobuildersusers.rest.dto.UserDTO;
import com.angelperez.iobuildersusers.rest.mapper.UsersMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class UsersControllerTest {

    private UsersController usersController;

    private UsersService usersService;

    @BeforeEach
    void setUp() {
        usersService = Mockito.mock(UsersService.class);
        usersController = new UsersController(usersService, new UsersMapperImpl());
    }

    @Test
    public void getUserById_onExistingUser_returnsTheUser() {
        Mockito.when(usersService.getUser("testId")).thenReturn(Mono.just(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname")));

        Mono<ResponseEntity<UserDTO>> result = usersController.getUserById("testId");

        assertThat(result.block()).satisfies(
            res -> {
                assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(res.getBody()).isEqualTo(new UserDTO()
                    .setId("testId")
                    .setEmail("testEmail")
                    .setPhone(666999666)
                    .setName("testName")
                    .setSurname("testSurname"));
            }
        );
    }

    @Test
    public void getUserById_onNotExistingUser_returnsNotFound() {
        Mockito.when(usersService.getUser("testId")).thenReturn(Mono.empty());

        Mono<ResponseEntity<UserDTO>> result = usersController.getUserById("testId");

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void createUser_onNewUser_returnsCreated() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.saveUser(user)).thenReturn(Mono.just(OperationResult.OK));

        Mono<ResponseEntity<Void>> result = usersController.createUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void createUser_onExistingUser_returnsConflict() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.saveUser(user)).thenReturn(Mono.just(OperationResult.CONFLICT));

        Mono<ResponseEntity<Void>> result = usersController.createUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void createUser_onErrorAtSaving_returnsError() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.saveUser(user)).thenReturn(Mono.just(OperationResult.ERROR));

        Mono<ResponseEntity<Void>> result = usersController.createUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateUser_onExistingUser_returnsOk() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.updateUser(user)).thenReturn(Mono.just(OperationResult.OK));

        Mono<ResponseEntity<Void>> result = usersController.updateUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateUser_onNewUser_returnsNotFound() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.updateUser(user)).thenReturn(Mono.just(OperationResult.NOT_FOUND));

        Mono<ResponseEntity<Void>> result = usersController.updateUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateUser_onErrorAtUpdating_returnsError() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersService.updateUser(user)).thenReturn(Mono.just(OperationResult.ERROR));

        Mono<ResponseEntity<Void>> result = usersController.updateUser(new UserDTO()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void deleteUser_onExistingUser_returnsOk() {
        Mockito.when(usersService.deleteUser("testId")).thenReturn(Mono.just(OperationResult.OK));

        Mono<ResponseEntity<Void>> result = usersController.deleteUser("testId");

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteUser_onNewUser_returnsNotFound() {
        Mockito.when(usersService.deleteUser("testId")).thenReturn(Mono.just(OperationResult.NOT_FOUND));

        Mono<ResponseEntity<Void>> result = usersController.deleteUser("testId");

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteUser_onErrorAtUpdating_returnsError() {
        Mockito.when(usersService.deleteUser("testId")).thenReturn(Mono.just(OperationResult.ERROR));

        Mono<ResponseEntity<Void>> result = usersController.deleteUser("testId");

        assertThat(result.block())
            .isNotNull()
            .extracting(ResponseEntity::getStatusCode)
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
