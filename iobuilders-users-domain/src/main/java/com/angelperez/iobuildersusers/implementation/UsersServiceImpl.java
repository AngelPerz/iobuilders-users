package com.angelperez.iobuildersusers.implementation;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.model.User;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UsersRepositoryPort usersRepositoryPort;

    @Override
    public Mono<User> getUser(String id) {
        return usersRepositoryPort.getUser(id);
    }

    @Override
    public Mono<OperationResult> saveUser(User user) {
        return usersRepositoryPort.saveUser(user);
    }

    @Override
    public Mono<OperationResult> updateUser(User user) {
        return usersRepositoryPort.updateUser(user);
    }

    @Override
    public Mono<OperationResult> deleteUser(String id) {
        return usersRepositoryPort.deleteUser(id);
    }

}
