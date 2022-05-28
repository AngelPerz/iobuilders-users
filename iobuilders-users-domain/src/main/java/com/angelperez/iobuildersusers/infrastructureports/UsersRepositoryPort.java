package com.angelperez.iobuildersusers.infrastructureports;

import com.angelperez.iobuildersusers.model.User;
import reactor.core.publisher.Mono;

public interface UsersRepositoryPort {

    Mono<User> getUser(String id);

    Mono<User> saveUser(User user);

    Mono<User> updateUser(User user);

    Mono<Void> deleteUser(String id);
}
