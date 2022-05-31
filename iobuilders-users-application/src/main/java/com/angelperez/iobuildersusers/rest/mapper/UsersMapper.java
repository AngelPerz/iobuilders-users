package com.angelperez.iobuildersusers.rest.mapper;

import com.angelperez.iobuildersusers.model.User;
import com.angelperez.iobuildersusers.rest.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UsersMapper {

    UserDTO toDTO(User user);

    User toDomainModel(UserDTO userDTO);

}
