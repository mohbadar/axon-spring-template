package com.unite.axon_spring.auth.aggregate;

import com.unite.axon_spring.auth.command.DeletePermissionCommand;
import com.unite.axon_spring.auth.command.RegisterPermissionCommand;
import com.unite.axon_spring.auth.command.UpdatePermissionCommand;
import com.unite.axon_spring.auth.event.PermissionCreatedEvent;
import com.unite.axon_spring.auth.event.PermissionDeletedEvent;
import com.unite.axon_spring.auth.event.PermissionUpdatedEvent;
import com.unite.axon_spring.auth.model.Permission;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.UUID;

@Aggregate
public class PermissionAggregator {

    private String permissionId;
    private String name;
    private String description;
    private boolean active;

    @AggregateIdentifier
    private String getAggregateIdentifier() {
        return (null != permissionId) ? permissionId +"": name;
    }


    protected PermissionAggregator(){
        //for Axon Instantiation
    }

    @CommandHandler
    public PermissionAggregator(RegisterPermissionCommand cmd)
    {
        Assert.notNull(cmd.getName(), "Ït is required");
        Assert.notNull(cmd.getDescription(), "Ït is required");
        Assert.notNull(cmd.isActive(), "Ït is required");
        AggregateLifecycle.apply(new PermissionCreatedEvent(cmd.getPermissionId(),cmd.getName(), cmd.getDescription(), cmd.isActive()));
    }

    @CommandHandler
    public void on(UpdatePermissionCommand cmd)
    {
        System.out.println(cmd.toString());
        Assert.notNull(cmd.getName(), "Ït is required");
        Assert.notNull(cmd.getDescription(), "Ït is required");
        Assert.notNull(cmd.getDescription(), "Ït is required");
        Assert.notNull(cmd.getPermissionId(), "Ït is required");
        AggregateLifecycle.apply(new PermissionUpdatedEvent(cmd.getPermissionId(),cmd.getName(), cmd.getDescription(), cmd.isActive()));
    }

    @CommandHandler
    public void on(DeletePermissionCommand cmd)
    {
        Assert.notNull(cmd.getPermissionId(), "Ït is required");
        AggregateLifecycle.apply(new PermissionDeletedEvent(cmd.getPermissionId()));
    }

    @EventSourcingHandler
    private void on(PermissionCreatedEvent event)
    {
        permissionId = event.getPermissionId();
        name=event.getName();
        description=event.getDescription();
        active=event.isActive();
    }

    @EventSourcingHandler
    private void on(PermissionUpdatedEvent event)
    {
        name=event.getName();
        description=event.getDescription();
        active=event.isActive();
    }
    
}
