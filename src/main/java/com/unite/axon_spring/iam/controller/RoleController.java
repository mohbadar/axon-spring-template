package com.unite.axon_spring.iam.controller;

import com.unite.axon_spring.iam.audit.annotation.Auditable;
import com.unite.axon_spring.iam.command.*;
import com.unite.axon_spring.iam.controller.resource.RoleVO;
import com.unite.axon_spring.iam.controller.resource.RolesListVO;
import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.dto.RoleDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.model.Permission;
import com.unite.axon_spring.iam.query.GetEnvironmentQuery;
import com.unite.axon_spring.iam.query.GetEnvironmentsQuery;
import com.unite.axon_spring.iam.query.GetRoleQuery;
import com.unite.axon_spring.iam.query.GetRolesQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/roles", produces = "application/hal+json")
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
    public ResponseEntity<RolesListVO> getRoles() throws ExecutionException, InterruptedException {
        List<RoleVO> roles = queryGateway.query(new GetRolesQuery(), ResponseTypes.multipleInstancesOf(RoleVO.class)).get();

        RolesListVO rolesListVO = new RolesListVO();

        for (RoleVO role : roles) {
            // Adding self link role 'singular' resource
            Link selfLink = linkTo(RoleController.class)
                    .slash(role.getId()).withSelfRel();

            ResponseEntity<HttpStatus> methodLinkBuilder =
                    methodOn(RoleController.class)
                            .deactivateRole(role.getId());
            Link deactivateRole =
                    linkTo(methodLinkBuilder).withRel("deactivate-role");

            ResponseEntity<List<Permission>> permissionsOfRolesLink =
                    methodOn(PermissionController.class)
                            .getPermissions();
            Link rolePermissions =
                    linkTo(permissionsOfRolesLink).withRel("role-permissions");
            // Add link to singular resource
            role.add(selfLink);
            role.add(deactivateRole);
            role.add(rolePermissions);
            rolesListVO.getRoles().add(role);
        }

        // Adding self link employee collection resource
        Link selfLink =
                linkTo(methodOn(RoleController.class).getRoles())
                        .withSelfRel();
        // Add link to collection resource
        rolesListVO.add(selfLink);

        return ResponseEntity.ok(rolesListVO);
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<RoleFullViewDTO> getRole(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<RoleFullViewDTO> future = queryGateway.query(new GetRoleQuery(id), RoleFullViewDTO.class);

        RoleFullViewDTO dto = future.get();
        Link link = linkTo(RoleController.class)
                .slash(dto.getId()).withSelfRel();



        return ResponseEntity.ok(dto);
    }

}
