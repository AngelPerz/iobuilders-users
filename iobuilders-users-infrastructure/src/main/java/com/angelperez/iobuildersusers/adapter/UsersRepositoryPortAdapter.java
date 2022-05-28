package com.angelperez.iobuildersusers.adapter;

import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.mapper.UserEntitiesMapper;
import com.angelperez.iobuildersusers.model.User;
import com.angelperez.iobuildersusers.r2dbc.repository.UsersRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UsersRepositoryPortAdapter implements UsersRepositoryPort {

    UsersRepository usersRepository;

    UserEntitiesMapper userEntitiesMapper;

    @Override
    public Mono<User> getUser(String id) {
        return usersRepository.findById(id).map(t -> userEntitiesMapper.toDomainModel(t));
    }
}
