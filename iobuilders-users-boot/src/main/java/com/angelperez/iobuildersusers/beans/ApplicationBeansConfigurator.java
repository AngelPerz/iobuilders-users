package com.angelperez.iobuildersusers.beans;

import com.angelperez.iobuildersusers.adapter.UsersRepositoryPortAdapter;
import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.infrastructureports.UsersRepositoryPort;
import com.angelperez.iobuildersusers.mapper.UserEntitiesMapper;
import com.angelperez.iobuildersusers.r2dbc.repository.UsersRepository;
import com.angelperez.iobuildersusers.implementation.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeansConfigurator {

    @Bean
    public UsersService getUsersService(UsersRepositoryPort usersRepositoryPort) {
        return new UsersServiceImpl(usersRepositoryPort);
    }

    @Bean
    public UsersRepositoryPort getUsersRepository(UsersRepository usersRepository, UserEntitiesMapper userEntitiesMapper) {
        return new UsersRepositoryPortAdapter(usersRepository, userEntitiesMapper);
    }
}
