package com.unite.axon_spring.iam.commandmodel.aggregate;


import com.unite.axon_spring.iam.coreapi.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
@NoArgsConstructor
public class RoleAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private List<String> permissionIds;

    @CommandHandler
    public RoleAggregate(CreateRoleCommand cmd)
    {
        apply(new RoleCreatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getActive(),
                cmd.getEnvSlug(),
                cmd.getPermissionIds()
        ));
    }

    @CommandHandler
    public void  handle(UpdateRoleCommand cmd){
        apply(new RoleUpdatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getActive(),
                cmd.getEnvSlug(),
                cmd.getPermissionIds()
        ));
    }


    @CommandHandler
    public void  handle(DeactivateRoleCommand cmd){
        apply(new RoleDeactivatedEvent(
                cmd.getId()
        ));
    }

    @EventSourcingHandler
    public void on(RoleCreatedEvent event)
    {
        this.id=event.getId();
        this.name=event.getName();
        this.description=event.getDescription();
        this.active=event.getActive();
        this.envSlug=event.getEnvSlug();
        this.permissionIds=event.getPermissionIds();
    }

    @EventSourcingHandler
    public void on(RoleUpdatedEvent event)
    {
        this.name=event.getName();
        this.description=event.getDescription();
        this.active=event.getActive();
        this.envSlug=event.getEnvSlug();
        this.permissionIds=event.getPermissionIds();
    }

    @EventSourcingHandler
    public void on(RoleDeactivatedEvent event)
    {
        this.active=false;
    }
}
