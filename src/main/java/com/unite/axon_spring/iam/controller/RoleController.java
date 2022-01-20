package com.unite.axon_spring.iam.controller;

import com.unite.axon_spring.iam.audit.annotation.Auditable;
import com.unite.axon_spring.iam.command.*;
import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.dto.RoleDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.query.GetEnvironmentQuery;
import com.unite.axon_spring.iam.query.GetEnvironmentsQuery;
import com.unite.axon_spring.iam.query.GetRoleQuery;
import com.unite.axon_spring.iam.query.GetRolesQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/api/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public RoleController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Auditable
    @PostMapping
    public ResponseEntity<HttpStatus> addRole(@Valid @RequestBody RoleDTO dto) throws ExecutionException, InterruptedException {
        dto.setId(UUID.randomUUID().toString());
        commandGateway.send(new CreateRoleCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getPermissionIds()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @Auditable
    @PutMapping
    public ResponseEntity<HttpStatus> updateRole(@Valid @RequestBody RoleDTO dto) throws ExecutionException, InterruptedException {
        commandGateway.send(new UpdateRoleCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getPermissionIds()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @Auditable
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deactivateRole(@PathVariable(required = true) String id){
        commandGateway.send(new DeactivateRoleCommand(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Auditable
    @GetMapping
    public ResponseEntity<List<RoleFullViewDTO>> getRoles() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetRolesQuery(), ResponseTypes.multipleInstancesOf(RoleFullViewDTO.class)).get());
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<RoleFullViewDTO> getRole(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<RoleFullViewDTO> future = queryGateway.query(new GetRoleQuery(id), RoleFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }

}
