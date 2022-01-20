package com.unite.axon_spring.iam.controller;

import com.unite.axon_spring.iam.audit.annotation.Auditable;
import com.unite.axon_spring.iam.command.*;
import com.unite.axon_spring.iam.dto.UserDTO;
import com.unite.axon_spring.iam.dto.UserFullViewDTO;
import com.unite.axon_spring.iam.query.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UserController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Auditable
    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody UserDTO dto) throws ExecutionException, InterruptedException {
        dto.setId(UUID.randomUUID().toString());
        commandGateway.send(new CreateUserCommand(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.isActive(),
                dto.getPhoneNo(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getEnvironmentIds(),
                dto.getGroupIds())
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Auditable
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserDTO dto) throws ExecutionException, InterruptedException {
        System.out.println(dto.toString());
        commandGateway.send(new UpdateUserCommand(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.isActive(),
                dto.getPhoneNo(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getEnvironmentIds(),
                dto.getGroupIds())
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @Auditable
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deactivateUser(@PathVariable(required = true) String id){
        commandGateway.send(new DeactivateUserCommand(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Auditable
    @GetMapping
    public ResponseEntity<List<UserFullViewDTO>> getUsers() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetUsersQuery(), ResponseTypes.multipleInstancesOf(UserFullViewDTO.class)).get());
    }

    @Auditable
    @GetMapping("/id/{id}")
    public ResponseEntity<UserFullViewDTO> getUserById(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<UserFullViewDTO> future = queryGateway.query(new GetUserByIdQuery(id), UserFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }

    @Auditable
    @GetMapping("/username/{username}")
    public ResponseEntity<UserFullViewDTO> getUserByUsername(@PathVariable(required = true) String username) throws ExecutionException, InterruptedException {
        CompletableFuture<UserFullViewDTO> future = queryGateway.query(new GetUserByUsernameQuery(username), UserFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }

    @Auditable
    @GetMapping("/email/{email}")
    public ResponseEntity<UserFullViewDTO> getUserByEmail(@PathVariable(required = true) String email) throws ExecutionException, InterruptedException {
        CompletableFuture<UserFullViewDTO> future = queryGateway.query(new GetUserByEmailQuery(email), UserFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }

    @Auditable
    @GetMapping("/env/{envSlug}")
    public ResponseEntity<List<UserFullViewDTO>> getUserByEnvSlug(@PathVariable(required = true) String envSlug) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetUserByEnvQuery(envSlug), ResponseTypes.multipleInstancesOf(UserFullViewDTO.class)).get());
    }


}
