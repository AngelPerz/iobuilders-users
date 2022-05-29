package com.angelperez.iobuildersusers.rest.controller;

import com.angelperez.iobuildersusers.applicationports.UsersService;
import com.angelperez.iobuildersusers.common.OperationResult;
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
        return usersService.getUser(id)
            .map(t -> ResponseEntity.ok(usersMapper.toDTO(t)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created"),
        @ApiResponse(responseCode = "409", description = "User already exists")})
    @PostMapping("/users")
    public Mono<ResponseEntity<Void>> createUser(@RequestBody UserDTO userDTO) {
        return usersService.saveUser(usersMapper.toDomainModel(userDTO))
            .map(res -> switch (res) {
                case OK -> new ResponseEntity<>(HttpStatus.CREATED);
                case CONFLICT -> new ResponseEntity<>(HttpStatus.CONFLICT);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping("/users")
    public Mono<ResponseEntity<UserDTO>> updateUser(@RequestBody UserDTO userDTO) {
        return usersService.updateUser(usersMapper.toDomainModel(userDTO))
            .map(res -> switch (res) {
                case OK -> new ResponseEntity<>(HttpStatus.OK);
                case NOT_FOUND -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable(value = "id") String id) {
        return usersService.deleteUser(id)
            .map(res -> switch (res) {
                case OK -> new ResponseEntity<>(HttpStatus.OK);
                case NOT_FOUND -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }
}
