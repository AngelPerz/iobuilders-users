package com.angelperez.iobuildersusers.rest;

import com.angelperez.iobuildersusers.applicationports.UsersService;
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

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved for the given id"),
        @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/users/{id}")
    public Mono<ResponseEntity<String>> getTweetById(@PathVariable(value = "id") String id) {
        return Mono.just(ResponseEntity.ok(usersService.getUser(id)));
    }
}
