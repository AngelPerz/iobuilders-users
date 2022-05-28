package com.angelperez.iobuildersusers.mapper;

import com.angelperez.iobuildersusers.r2dbc.entity.UserEntity;
import com.angelperez.iobuildersusers.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserEntitiesMapper {

    UserEntity toEntity (User user);

    User toDomainModel (UserEntity userEntity);

}
