package com.unite.axon_spring.auth.aggregate;

import com.unite.axon_spring.auth.command.RegisterPermissionCommand;
import com.unite.axon_spring.auth.event.PermissionCreatedEvent;
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

    @AggregateIdentifier
    private String permissionId;
    private String name;
    private String description;
    private boolean active;

    protected PermissionAggregator(){
        //for Axon Instantiation
    }

    @CommandHandler
    public PermissionAggregator(RegisterPermissionCommand cmd)
    {
//        Assert.notNull(cmd.getName(), "Name is required");
//        Assert.notNull(cmd.getDescription(), "Description is required");
//        Assert.notNull(cmd.isActive(), "Active is required");

        System.out.println("CommandHandler: " + cmd.getName());
        System.out.println("CommandHandler: " + cmd.getDescription());
        System.out.println("CommandHandler: " + cmd.isActive());
        AggregateLifecycle.apply(new PermissionCreatedEvent(cmd.getPermissionId(),cmd.getName(), cmd.getDescription(), cmd.isActive()));
    }

    @EventSourcingHandler
    private void on(PermissionCreatedEvent event)
    {
        permissionId = event.getPermissionId();
        name=event.getName();
        description=event.getDescription();
        active=event.isActive();
    }


}
