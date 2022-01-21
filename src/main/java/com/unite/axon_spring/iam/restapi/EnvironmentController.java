package com.unite.axon_spring.iam.restapi;

import com.unite.axon_spring.iam.common.audit.Auditable;
import com.unite.axon_spring.iam.coreapi.command.CreateEnvironmentCommand;
import com.unite.axon_spring.iam.coreapi.command.DeactivateEnvironmentCommand;
import com.unite.axon_spring.iam.coreapi.command.UpdateEnvironmentCommand;
import com.unite.axon_spring.iam.common.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.coreapi.query.GetEnvironmentQuery;
import com.unite.axon_spring.iam.coreapi.query.GetEnvironmentsQuery;
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
    public CompletableFuture<String> addEnvironment(@Valid @RequestBody EnvironmentDTO dto) throws ExecutionException, InterruptedException {
        String id = Objects.isNull(dto.getId()) ? UUID.randomUUID().toString(): dto.getId();
        dto.setId(id);
       return commandGateway.send(new CreateEnvironmentCommand(
                dto.getId(),
                dto.getSlug(),
                dto.getName(),
                dto.getDescription(),
                dto.getSecretKey(),
                dto.isActive()
                )
        );
    }


    @Auditable
    @PutMapping
    public CompletableFuture<String> updateEnvironment(@Valid @RequestBody EnvironmentDTO dto) throws ExecutionException, InterruptedException {
        return commandGateway.send(new UpdateEnvironmentCommand(
                        dto.getId(),
                        dto.getSlug(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.getSecretKey(),
                        dto.isActive()
                )
        );
    }

    @Auditable
    @DeleteMapping("/{id}")
    public CompletableFuture<String> deactivateEnvironment(@PathVariable(required = true) String id){
        return commandGateway.send(new DeactivateEnvironmentCommand(id));
    }

    @Auditable
    @GetMapping
    public ResponseEntity<CompletableFuture<List<EnvironmentDTO>>> getEnvironments() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetEnvironmentsQuery(), ResponseTypes.multipleInstancesOf(EnvironmentDTO.class)));
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<CompletableFuture<EnvironmentDTO>> getEnvironment(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<EnvironmentDTO> future = queryGateway.query(new GetEnvironmentQuery(id), EnvironmentDTO.class);
        return ResponseEntity.ok(future);
    }


}
