package com.unite.axon_spring.iam.controller;

import com.unite.axon_spring.iam.audit.annotation.Auditable;
import com.unite.axon_spring.iam.command.CreateEnvironmentCommand;
import com.unite.axon_spring.iam.command.DeactivateEnvironmentCommand;
import com.unite.axon_spring.iam.command.UpdateEnvironmentCommand;
import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.query.GetEnvironmentQuery;
import com.unite.axon_spring.iam.query.GetEnvironmentsQuery;
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
@RequestMapping(path = "/api/environments")
public class EnvironmentController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public EnvironmentController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Auditable
    @PostMapping
    public ResponseEntity<HttpStatus> addEnvironment(@Valid @RequestBody EnvironmentDTO dto) throws ExecutionException, InterruptedException {
        dto.setId(UUID.randomUUID().toString());
        commandGateway.send(new CreateEnvironmentCommand(
                dto.getId(),
                dto.getSlug(),
                dto.getName(),
                dto.getDescription(),
                dto.getSecretKey(),
                dto.isActive()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    @Auditable
    @PutMapping
    public ResponseEntity<HttpStatus> updateEnvironment(@Valid @RequestBody EnvironmentDTO dto) throws ExecutionException, InterruptedException {
        commandGateway.send(new UpdateEnvironmentCommand(
                        dto.getId(),
                        dto.getSlug(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.getSecretKey(),
                        dto.isActive()
                )
        );
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Auditable
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deactivateEnvironment(@PathVariable(required = true) String id){
        commandGateway.send(new DeactivateEnvironmentCommand(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Auditable
    @GetMapping
    public ResponseEntity<List<EnvironmentDTO>> getEnvironments() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetEnvironmentsQuery(), ResponseTypes.multipleInstancesOf(EnvironmentDTO.class)).get());
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentDTO> getEnvironment(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<EnvironmentDTO> future = queryGateway.query(new GetEnvironmentQuery(id), EnvironmentDTO.class);
        return ResponseEntity.ok(future.get());
    }


}
