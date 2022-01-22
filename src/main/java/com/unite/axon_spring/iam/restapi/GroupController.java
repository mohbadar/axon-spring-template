package com.unite.axon_spring.iam.restapi;

import com.unite.axon_spring.iam.common.audit.Auditable;
import com.unite.axon_spring.iam.coreapi.*;
import com.unite.axon_spring.iam.common.dto.GroupDTO;
import com.unite.axon_spring.iam.common.dto.GroupFullViewDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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

    @Auditable
    @PostMapping
    public CompletableFuture<String> addGroup(@Valid @RequestBody GroupDTO dto) throws ExecutionException, InterruptedException {
        String id = Objects.isNull(dto.getId()) ? UUID.randomUUID().toString(): dto.getId();
        dto.setId(id);
        return commandGateway.send(new CreateGroupCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getRoleIds()
                )
        );
    }


    @Auditable
    @PutMapping
    public CompletableFuture<String> updateGroup(@Valid @RequestBody GroupDTO dto) throws ExecutionException, InterruptedException {
        return commandGateway.send(new UpdateGroupCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getRoleIds()
                )
        );
    }

    @Auditable
    @DeleteMapping("/{id}")
    public CompletableFuture<String> deactivateGroup(@PathVariable(required = true) String id){
        return commandGateway.send(new DeactivateGroupCommand(id));
    }

    @Auditable
    @GetMapping
    public ResponseEntity<List<GroupFullViewDTO>> getGroups() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetGroupsQuery(), ResponseTypes.multipleInstancesOf(GroupFullViewDTO.class)).get());
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<GroupFullViewDTO> getGroup(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<GroupFullViewDTO> future = queryGateway.query(new GetGroupQuery(id), GroupFullViewDTO.class);
        return ResponseEntity.ok(future.get());
    }
}
