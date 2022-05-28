package com.angelperez.iobuildersusers.adapter;

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
    public Mono<User> saveUser(User user) {
        return usersRepository.save(userEntitiesMapper.toEntity(user))
            .map(userEntitiesMapper::toDomainModel)
            .doOnError(err -> log.error(String.format("Error saving user with id [%s]", user.getId()), err))
            .onErrorReturn(new User());
    }

    @Override
    public Mono<User> updateUser(User user) {
        return usersRepository.findById(user.getId())
            .map(entity -> mergeUserProperties(entity, user))
            .doOnNext(entity -> usersRepository.save(entity))
            .map(userEntitiesMapper::toDomainModel);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return usersRepository.deleteById(id);
    }

    private UserEntity mergeUserProperties(UserEntity userEntity, User user) {
        return userEntity.setName(user.getName())
            .setSurname(user.getSurname())
            .setEmail(user.getEmail())
            .setPhone(user.getPhone());
    }
}
