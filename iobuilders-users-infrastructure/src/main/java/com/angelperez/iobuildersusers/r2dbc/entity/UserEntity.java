package com.angelperez.iobuildersusers.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "users")
@Accessors(chain = true)
public class UserEntity {

    @Id
    private String id;

    private String name;

    private String surname;

    private String email;

    private Integer phone;

}
