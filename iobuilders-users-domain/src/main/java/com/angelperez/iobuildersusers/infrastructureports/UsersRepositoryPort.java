package com.angelperez.iobuildersusers.infrastructureports;

import com.angelperez.iobuildersusers.model.User;
import reactor.core.publisher.Mono;

public interface UsersRepositoryPort {

    Mono<User> getUser(String id);
}
