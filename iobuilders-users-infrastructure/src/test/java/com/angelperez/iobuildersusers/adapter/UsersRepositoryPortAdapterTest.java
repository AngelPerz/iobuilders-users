package com.angelperez.iobuildersusers.adapter;

import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.mapper.UserEntitiesMapperImpl;
import com.angelperez.iobuildersusers.model.User;
import com.angelperez.iobuildersusers.r2dbc.entity.UserEntity;
import com.angelperez.iobuildersusers.r2dbc.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class UsersRepositoryPortAdapterTest {

    private UsersRepository usersRepository;

    private UsersRepositoryPortAdapter usersRepositoryPortAdapter;

    @BeforeEach
    void setUp() {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersRepositoryPortAdapter = new UsersRepositoryPortAdapter(usersRepository, new UserEntitiesMapperImpl());
    }

    @Test
    public void getUser_onExistingUser_returnsTheUser() {
        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.just(new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname")));

        Mono<User> result = usersRepositoryPortAdapter.getUser("testId");

        assertThat(result.block()).isEqualTo(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));
    }

    @Test
    public void getUser_onNotExistingUser_returnsEmpty() {
        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.empty());

        Mono<User> result = usersRepositoryPortAdapter.getUser("testId");

        assertThat(result.block()).isNull();
    }

    @Test
    public void saveUser_onNewUser_returnsOK() {
        UserEntity entity = new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.empty());
        Mockito.when(usersRepository.save(Mockito.any())).thenReturn(Mono.just(entity));

        Mono<OperationResult> result = usersRepositoryPortAdapter.saveUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.OK);
    }

    @Test
    public void saveUser_onExistingUser_returnsConflict() {
        UserEntity entity = new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.just(entity));

        Mono<OperationResult> result = usersRepositoryPortAdapter.saveUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.CONFLICT);
    }

    @Test
    public void saveUser_onErrorSaving_returnsError() {
        Mockito.when(usersRepository.findById("testId"))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.saveUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }

    @Test
    public void saveUser_onErrorSaving_returnsError2() {
        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.empty());
        Mockito.when(usersRepository.save(Mockito.any()))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.saveUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }

    @Test
    public void updateUser_onExistingUser_returnsOK() {
        UserEntity entity = new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.just(entity));
        Mockito.when(usersRepository.save(Mockito.any())).thenReturn(Mono.just(entity));

        Mono<OperationResult> result = usersRepositoryPortAdapter.updateUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.OK);
    }

    @Test
    public void updateUser_onNewUser_returnsNotFound() {
        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.empty());

        Mono<OperationResult> result = usersRepositoryPortAdapter.updateUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.NOT_FOUND);
    }

    @Test
    public void updateUser_onErrorSaving_returnsError() {
        Mockito.when(usersRepository.findById("testId"))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.updateUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }

    @Test
    public void updateUser_onErrorSaving_returnsError2() {
        UserEntity entity = new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.just(entity));
        Mockito.when(usersRepository.save(Mockito.any()))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.updateUser(new User()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname"));

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }

    @Test
    public void deleteUser_onNewUser_returnsNotFound() {
        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.empty());

        Mono<OperationResult> result = usersRepositoryPortAdapter.deleteUser("testId");

        assertThat(result.block()).isEqualTo(OperationResult.NOT_FOUND);
    }

    @Test
    public void deleteUser_onErrorDeleting_returnsError() {
        Mockito.when(usersRepository.findById("testId"))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.deleteUser("testId");

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }

    @Test
    public void deleteUser_onErrorDeleting_returnsError2() {
        UserEntity entity = new UserEntity()
            .setId("testId")
            .setEmail("testEmail")
            .setPhone(666999666)
            .setName("testName")
            .setSurname("testSurname");

        Mockito.when(usersRepository.findById("testId")).thenReturn(Mono.just(entity));
        Mockito.when(usersRepository.deleteById("testId"))
            .thenReturn(Mono.error(new DataIntegrityViolationException("ERROR TEST")));

        Mono<OperationResult> result = usersRepositoryPortAdapter.deleteUser("testId");

        assertThat(result.block()).isEqualTo(OperationResult.ERROR);
    }
}
