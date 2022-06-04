package com.angelperez.iobuildersusers.implementation;

import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class UsersServiceImplTest {

    private UsersRepositoryPort usersRepositoryPort;

    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        usersRepositoryPort = Mockito.mock(UsersRepositoryPort.class);
        usersService = new UsersServiceImpl(usersRepositoryPort);
    }

    @Test
    public void getUser_onAnyCall_returnsTheRepositoryPortResult() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepositoryPort.getUser("testId")).thenReturn(Mono.just(user));

        Mono<User> result = usersService.getUser("testId");

        assertThat(result.block()).isEqualTo(user);
    }

    @Test
    public void saveUser_onAnyCall_returnsTheRepositoryPortResult() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepositoryPort.saveUser(user)).thenReturn(Mono.just(OperationResult.OK));

        Mono<OperationResult> result = usersService.saveUser(user);

        assertThat(result.block()).isEqualTo(OperationResult.OK);
    }

    @Test
    public void updateUser_onAnyCall_returnsTheRepositoryPortResult() {
        User user = new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepositoryPort.updateUser(user)).thenReturn(Mono.just(OperationResult.OK));

        Mono<OperationResult> result = usersService.updateUser(user);

        assertThat(result.block()).isEqualTo(OperationResult.OK);
    }

    @Test
    public void deleteUser_onAnyCall_returnsTheRepositoryPortResult() {
        Mockito.when(usersRepositoryPort.deleteUser("testId")).thenReturn(Mono.just(OperationResult.OK));

        Mono<OperationResult> result = usersService.deleteUser("testId");

        assertThat(result.block()).isEqualTo(OperationResult.OK);
    }
}
