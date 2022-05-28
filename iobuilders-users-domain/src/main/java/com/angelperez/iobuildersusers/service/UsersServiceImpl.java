package com.angelperez.iobuildersusers.service;

import com.angelperez.iobuildersusers.applicationports.UsersService;

public class UsersServiceImpl implements UsersService {

    @Override
    public String getUser(String id) {
        return id;
    }
}
