package com.angelperez.iobuildersusers.service;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.model.User;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    UsersRepositoryPort usersRepositoryPort;

    @Override
    public Mono<User> getUser(String id) {
        return usersRepositoryPort.getUser(id);
    }
}
