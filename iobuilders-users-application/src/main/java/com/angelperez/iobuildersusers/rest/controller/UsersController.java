package com.angelperez.iobuildersusers.rest.controller;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.rest.dto.UserDTO;
import com.angelperez.iobuildersusers.rest.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class UsersController {

    private UsersService usersService;

    private UsersMapper usersMapper;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved for the given id"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable(value = "id") String id) {
        return usersService.getUser(id).map(t -> ResponseEntity.ok(usersMapper.toDTO(t)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
