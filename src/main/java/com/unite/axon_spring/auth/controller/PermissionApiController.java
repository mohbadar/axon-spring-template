package com.unite.axon_spring.auth.controller;

import com.unite.axon_spring.auth.command.RegisterPermissionCommand;
import com.unite.axon_spring.auth.dto.PermissionDTO;
import com.unite.axon_spring.auth.model.Permission;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(path = "")
    public HttpStatus addPermission(@RequestBody PermissionDTO dto)
    {
        System.out.println("Permissions: "+ dto.getName());
        commandGateway.send(new RegisterPermissionCommand(dto.getName(), dto.getDescription(), dto.isActive()));
        return HttpStatus.OK;
    }
}
