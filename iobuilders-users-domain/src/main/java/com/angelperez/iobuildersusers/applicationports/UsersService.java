package com.angelperez.iobuildersusers.applicationports;

import com.angelperez.iobuildersusers.model.User;
import reactor.core.publisher.Mono;

public interface UsersService {

    Mono<User> getUser(String id);
}
