package com.angelperez.iobuildersusers.applicationports;

import com.angelperez.iobuildersusers.common.OperationResult;
import com.angelperez.iobuildersusers.model.User;
import reactor.core.publisher.Mono;

public interface UsersService {

    Mono<User> getUser(String id);

    Mono<OperationResult> saveUser(User user);

    Mono<OperationResult> updateUser(User user);

    Mono<OperationResult> deleteUser(String id);
}
