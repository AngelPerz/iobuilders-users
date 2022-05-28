package com.angelperez.iobuildersusers.rest.controller;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.rest.dto.UserDTO;
import com.angelperez.iobuildersusers.rest.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created"),
        @ApiResponse(responseCode = "409", description = "User already exists")})
    @PostMapping("/users")
    public Mono<ResponseEntity<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        return usersService.saveUser(usersMapper.toDomainModel(userDTO)).map(t -> new ResponseEntity<>(usersMapper.toDTO(t), HttpStatus.CREATED))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping("/users")
    public Mono<ResponseEntity<UserDTO>> updateUser(@RequestBody UserDTO userDTO) {
        return usersService.updateUser(usersMapper.toDomainModel(userDTO)).map(t -> ResponseEntity.ok(usersMapper.toDTO(t)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable(value = "id") String id) {
        return usersService.deleteUser(id).map(t -> ResponseEntity.ok().build());
    }
}
