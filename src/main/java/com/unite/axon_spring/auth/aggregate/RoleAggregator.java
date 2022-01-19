package com.unite.axon_spring.auth.aggregate;

import com.unite.axon_spring.auth.command.RegisterRoleCommand;
import com.unite.axon_spring.auth.dto.RoleDTO;
import com.unite.axon_spring.auth.event.RoleCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.List;

@Aggregate
public class RoleAggregator {
    private Long roleId;
    private String name;
    private String description;
    private List<String> permissionIds;


    @AggregateIdentifier
    private String getAggregateIdentifier() {
        return (null != roleId) ? roleId +"": name;
    }


    protected RoleAggregator(){}

    @CommandHandler
    public RoleAggregator(RegisterRoleCommand cmd)
    {
        System.out.println(cmd.toString());
        Assert.notNull(cmd.getRoleDTO().getName(), "Name is required");
        AggregateLifecycle.apply(new RoleCreatedEvent(cmd.getRoleDTO()));
    }

    @EventSourcingHandler
    private void on(RoleCreatedEvent event)
    {
        this.name = event.getRoleDTO().getName();
        this.description = event.getRoleDTO().getDescription();
        this.permissionIds = event.getRoleDTO().getPermissionIds();
        this.roleId = event.getRoleDTO().getId();
    }
}
