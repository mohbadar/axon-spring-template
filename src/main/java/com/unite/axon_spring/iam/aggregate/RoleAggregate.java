package com.unite.axon_spring.iam.aggregate;

import com.unite.axon_spring.iam.command.CreateRoleCommand;
import com.unite.axon_spring.iam.command.DeactivateRoleCommand;
import com.unite.axon_spring.iam.command.UpdateRoleCommand;
import com.unite.axon_spring.iam.event.RoleCreatedEvent;
import com.unite.axon_spring.iam.event.RoleDeactivatedEvent;
import com.unite.axon_spring.iam.event.RoleUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Column;
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
                cmd.isActive(),
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
                cmd.isActive(),
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
        this.active=event.isActive();
        this.envSlug=event.getEnvSlug();
        this.permissionIds=event.getPermissionIds();
    }

    @EventSourcingHandler
    public void on(RoleUpdatedEvent event)
    {
        this.name=event.getName();
        this.description=event.getDescription();
        this.active=event.isActive();
        this.envSlug=event.getEnvSlug();
        this.permissionIds=event.getPermissionIds();
    }

    @EventSourcingHandler
    public void on(RoleDeactivatedEvent event)
    {
        this.active=false;
    }
}
