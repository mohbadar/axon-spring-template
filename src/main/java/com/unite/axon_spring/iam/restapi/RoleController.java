package com.unite.axon_spring.iam.restapi;

import com.unite.axon_spring.iam.common.audit.Auditable;
import com.unite.axon_spring.iam.coreapi.*;
import com.unite.axon_spring.iam.restapi.resource.RoleVO;
import com.unite.axon_spring.iam.restapi.resource.RolesListVO;
import com.unite.axon_spring.iam.commandmodel.model.Permission;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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
    public CompletableFuture<String> addRole(@Valid @RequestBody RoleVO dto) throws ExecutionException, InterruptedException {
        String id = Objects.isNull(dto.getId()) ? UUID.randomUUID().toString(): dto.getId();
        dto.setId(id);
        return commandGateway.send(new CreateRoleCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getPermissionIds()
                )
        );
    }


    @Auditable
    @PutMapping
    public CompletableFuture<String> updateRole(@Valid @RequestBody RoleVO dto) throws ExecutionException, InterruptedException {
        return commandGateway.send(new UpdateRoleCommand(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.isActive(),
                        dto.getEnvSlug(),
                        dto.getPermissionIds()
                )
        );
    }


    @Auditable
    @DeleteMapping("/{id}")
    public CompletableFuture<String> deactivateRole(@PathVariable(required = true) String id){
        return commandGateway.send(new DeactivateRoleCommand(id));
    }

    @Auditable
    @GetMapping
    public ResponseEntity<RolesListVO> getRoles() throws ExecutionException, InterruptedException {
        List<RoleVO> roles = queryGateway.query(new GetRolesQuery(), ResponseTypes.multipleInstancesOf(RoleVO.class)).get();
        RolesListVO rolesListVO = new RolesListVO();

        for (RoleVO role : roles) {
            Link selfLink = linkTo(RoleController.class)
                    .slash(role.getId()).withSelfRel();

            CompletableFuture<String> methodLinkBuilder =
                    methodOn(RoleController.class)
                            .deactivateRole(role.getId());
            Link deactivateRole =
                    linkTo(methodLinkBuilder).withRel("deactivate-role");

            ResponseEntity<List<Permission>> permissionsOfRolesLink =
                    methodOn(PermissionController.class)
                            .getPermissions();
            Link rolePermissions =
                    linkTo(permissionsOfRolesLink).withRel("role-permissions");
            role.add(selfLink);
            role.add(deactivateRole);
            role.add(rolePermissions);
            rolesListVO.getRoles().add(role);
        }
        Link selfLink =
                linkTo(methodOn(RoleController.class).getRoles())
                        .withSelfRel();
        rolesListVO.add(selfLink);

        return ResponseEntity.ok(rolesListVO);
    }

    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<RoleVO> getRole(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<RoleVO> future = queryGateway.query(new GetRoleQuery(id), RoleVO.class);
        RoleVO dto = future.get();
        Link link = linkTo(RoleController.class)
                .slash(dto.getId()).withSelfRel();
        return ResponseEntity.ok(dto);
    }

}
