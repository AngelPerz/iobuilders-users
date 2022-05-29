package com.angelperez.iobuildersusers.adapter;

import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.mapper.UserEntitiesMapper;
import com.angelperez.iobuildersusers.model.User;
import com.angelperez.iobuildersusers.r2dbc.entity.UserEntity;
import com.angelperez.iobuildersusers.r2dbc.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class UsersRepositoryPortAdapter implements UsersRepositoryPort {

    UsersRepository usersRepository;

    UserEntitiesMapper userEntitiesMapper;

    @Override
    public Mono<User> getUser(String id) {
        return usersRepository.findById(id).map(userEntitiesMapper::toDomainModel);
    }

    @Override
    public Mono<OperationResult> saveUser(User user) {
        return usersRepository.findById(user.getId())
            .hasElement()
            .flatMap(hasValue -> {
                if (hasValue) {
                    return Mono.just(OperationResult.CONFLICT);
                } else {
                    return usersRepository.save(userEntitiesMapper.toEntity(user).setNew(true))
                        .doOnError(err -> log.error(String.format("Error saving user with id [%s]", user.getId()), err))
                        .map(savedEntity -> OperationResult.OK)
                        .onErrorReturn(OperationResult.ERROR);
                }
            })
            .doOnError(err -> log.error(String.format("Error searching for user with id [%s]", user.getId()), err))
            .onErrorReturn(OperationResult.ERROR);
    }

    @Override
    public Mono<OperationResult> updateUser(User user) {
        return usersRepository.findById(user.getId())
            .flatMap(entity -> usersRepository.save(mergeUserProperties(entity, user).setNew(false))
                .doOnError(err -> log.error(String.format("Error updating user with id [%s]", user.getId()), err))
                .map(updatedEntity -> OperationResult.OK)
                .onErrorReturn(OperationResult.ERROR))
            .defaultIfEmpty(OperationResult.NOT_FOUND)
            .doOnError(err -> log.error(String.format("Error searching for user with id [%s]", user.getId()), err))
            .onErrorReturn(OperationResult.ERROR);
    }

    @Override
    public Mono<OperationResult> deleteUser(String id) {
        return usersRepository.findById(id)
            .hasElement()
            .flatMap(hasValue -> {
                if (!hasValue) {
                    return Mono.just(OperationResult.NOT_FOUND);
                } else {
                    return usersRepository.deleteById(id)
                        .doOnError(err -> log.error(String.format("Error deleting user with id [%s]", id), err))
                        .map(v -> OperationResult.OK)
                        .onErrorReturn(OperationResult.ERROR);
                }
            })
            .doOnError(err -> log.error(String.format("Error searching for user with id [%s]", id), err))
            .onErrorReturn(OperationResult.ERROR);
    }

    private UserEntity mergeUserProperties(UserEntity userEntity, User user) {
        return userEntity.setName(user.getName())
            .setSurname(user.getSurname())
            .setEmail(user.getEmail())
            .setPhone(user.getPhone());
    }
}
