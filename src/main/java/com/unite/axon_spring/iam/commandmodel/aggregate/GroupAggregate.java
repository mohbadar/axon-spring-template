package com.unite.axon_spring.iam.commandmodel.aggregate;

import com.unite.axon_spring.iam.coreapi.command.*;
import com.unite.axon_spring.iam.coreapi.event.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class GroupAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private List<String> roleIds;


    @CommandHandler
    public GroupAggregate(CreateGroupCommand cmd)
    {
        apply(new GroupCreatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.isActive(),
                cmd.getEnvSlug(),
                cmd.getRoleIds()
        ));
    }

    @CommandHandler
    public void  handle(UpdateGroupCommand cmd){
        apply(new GroupUpdatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.isActive(),
                cmd.getEnvSlug(),
                cmd.getRoleIds()
        ));
    }


    @CommandHandler
    public void  handle(DeactivateGroupCommand cmd){
        apply(new GroupDeactivatedEvent(
                cmd.getId()
        ));
    }

    @EventSourcingHandler
    public void on(GroupCreatedEvent event)
    {
        this.id=event.getId();
        this.name=event.getName();
        this.description=event.getDescription();
        this.active=event.isActive();
        this.envSlug=event.getEnvSlug();
        this.roleIds=event.getRoleIds();
    }

    @EventSourcingHandler
    public void on(GroupUpdatedEvent event)
    {
        this.name=event.getName();
        this.description=event.getDescription();
        this.active=event.isActive();
        this.envSlug=event.getEnvSlug();
        this.roleIds=event.getRoleIds();
    }

    @EventSourcingHandler
    public void on(GroupDeactivatedEvent event)
    {
        this.active=false;
    }
}
