package com.unite.axon_spring.iam.controller;

import com.unite.axon_spring.iam.command.*;
import com.unite.axon_spring.iam.dto.GroupDTO;
import com.unite.axon_spring.iam.dto.GroupFullViewDTO;
import com.unite.axon_spring.iam.dto.RoleDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.query.GetGroupQuery;
import com.unite.axon_spring.iam.query.GetGroupsQuery;
import com.unite.axon_spring.iam.query.GetRoleQuery;
import com.unite.axon_spring.iam.query.GetRolesQuery;
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
@RequestMapping(path = "/api/groups")
public class GroupController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public GroupController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addGroup(@Valid @RequestBody GroupDTO dto) throws ExecutionException, InterruptedException {
        dto.setId(UUID.randomUUID().toString());
        commandGateway.send(new CreateGroupCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getRoleIds()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @PutMapping
    public ResponseEntity<HttpStatus> updateGroup(@Valid @RequestBody GroupDTO dto) throws ExecutionException, InterruptedException {
        commandGateway.send(new UpdateGroupCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getRoleIds()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deactivateGroup(@PathVariable(required = true) String id){
        commandGateway.send(new DeactivateGroupCommand(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<GroupFullViewDTO>> getGroups() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetGroupsQuery(), ResponseTypes.multipleInstancesOf(GroupFullViewDTO.class)).get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupFullViewDTO> getGroup(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<GroupFullViewDTO> future = queryGateway.query(new GetGroupQuery(id), GroupFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }
}
