package com.unite.axon_spring.auth.aggregate;

import com.unite.axon_spring.auth.command.RegisterPermissionCommand;
import com.unite.axon_spring.auth.event.PermissionCreatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

@Aggregate
@Data
public class PermissionAggregator {


    @CommandHandler
    public void addPermission(RegisterPermissionCommand cmd)
    {
        Assert.notNull(cmd.getName(), "Name is required");
        Assert.notNull(cmd.getDescription(), "Description is required");
        Assert.notNull(cmd.isActive(), "Active is required");

        AggregateLifecycle.apply(new PermissionCreatedEvent(cmd.getName(), cmd.getDescription(), cmd.isActive()));
    }


}
