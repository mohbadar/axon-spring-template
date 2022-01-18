package com.unite.axon_spring.auth.controller;

import com.unite.axon_spring.auth.aggregate.PermissionAggregator;
import com.unite.axon_spring.auth.command.DeletePermissionCommand;
import com.unite.axon_spring.auth.command.RegisterPermissionCommand;
import com.unite.axon_spring.auth.command.UpdatePermissionCommand;
import com.unite.axon_spring.auth.dto.PermissionDTO;
import com.unite.axon_spring.auth.model.Permission;
import com.unite.axon_spring.auth.query.GetPermissionByIdQuery;
import com.unite.axon_spring.auth.query.GetPermissionsQuery;
import com.unite.axon_spring.library.aggregate.Library;
import com.unite.axon_spring.library.query.GetLibraryQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/permissions")
public class PermissionApiController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public PermissionApiController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path = "/register")
    public HttpStatus addPermission(@RequestBody PermissionDTO dto)
    {
//        System.out.println("Permissions: "+ dto.getName());
        dto.setPermissionId(UUID.randomUUID().toString());
        commandGateway.send(new RegisterPermissionCommand(dto.getPermissionId(),dto.getName(), dto.getDescription(), dto.isActive()));
        return HttpStatus.OK;
    }

    @GetMapping()
    public List<PermissionDTO> getPermissions() throws Exception
    {
        return queryGateway.query(new GetPermissionsQuery(), ResponseTypes.multipleInstancesOf(PermissionDTO.class)).get();
    }


    @GetMapping("/{permissionId}")
    public PermissionDTO getPermission(@PathVariable(required = true) String permissionId) throws Exception
    {
        CompletableFuture<PermissionDTO> future = queryGateway.query(new GetPermissionByIdQuery(permissionId), PermissionDTO.class);
        return future.get();
    }


    @DeleteMapping("/{permissionId}")
    public HttpStatus deletePermission(@PathVariable(required = true) String permissionId) throws Exception
    {
        commandGateway.send(new DeletePermissionCommand(permissionId));
        return HttpStatus.OK;
    }

    @PutMapping("/{permissionId}/update")
    public HttpStatus updatePermission(@PathVariable(required = true) String permissionId,@RequestBody PermissionDTO permissionDTO) throws Exception
    {
        permissionDTO.setPermissionId(permissionId);
        commandGateway.send(new UpdatePermissionCommand(permissionDTO.getPermissionId(), permissionDTO.getName(), permissionDTO.getDescription(), permissionDTO.isActive()));
        return HttpStatus.OK;
    }


}
