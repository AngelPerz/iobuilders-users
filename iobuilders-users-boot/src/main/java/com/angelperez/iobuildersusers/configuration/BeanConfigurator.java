package com.angelperez.iobuildersusers.configuration;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.service.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurator {

    @Bean
    public UsersService getUsersService(){
        return new UsersServiceImpl();
    }
}
