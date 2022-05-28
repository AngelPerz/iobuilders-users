package com.angelperez.iobuildersusers.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    private String id;

    private String name;

    private String surname;

    private String email;

    private Integer phone;

}
