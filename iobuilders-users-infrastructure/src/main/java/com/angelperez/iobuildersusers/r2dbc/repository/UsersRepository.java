package com.angelperez.iobuildersusers.r2dbc.repository;

import com.angelperez.iobuildersusers.r2dbc.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends ReactiveCrudRepository<UserEntity, String> {

}
