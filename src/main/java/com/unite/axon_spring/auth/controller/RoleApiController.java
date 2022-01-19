package com.unite.axon_spring.auth.controller;

import com.unite.axon_spring.auth.command.RegisterRoleCommand;
import com.unite.axon_spring.auth.dto.RoleDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/roles")
public class RoleApiController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public RoleApiController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path="")
    public HttpStatus addRole(@RequestBody RoleDTO dto)
    {
        commandGateway.send(new RegisterRoleCommand(dto));
        return HttpStatus.ACCEPTED;
    }
}
