package com.unite.axon_spring.iam.commandmodel.aggregate;

import com.unite.axon_spring.iam.coreapi.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.List;

@Aggregate
@NoArgsConstructor
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String address;
    private boolean active;
    private String phoneNo;
    private String username;
    private String password;
    private String email;
    private List<String> environmentIds;
    private List<String> groupIds;


    @CommandHandler
    public UserAggregate(CreateUserCommand cmd)
    {
        apply(new UserCreatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getAddress(),
                cmd.getActive(),
                cmd.getPhoneNo(),
                cmd.getUsername(),
                cmd.getPassword(),
                cmd.getEmail(),
                cmd.getEnvironmentIds(),
                cmd.getGroupIds()
        ));
    }

    @CommandHandler
    public void handle(UpdateUserCommand cmd)
    {
        System.out.println(cmd.toString());
        apply(new UserUpdatedEvent(
                cmd.getId(),
                cmd.getName(),
                cmd.getAddress(),
                cmd.getActive(),
                cmd.getPhoneNo(),
                cmd.getUsername(),
                cmd.getPassword(),
                cmd.getEmail(),
                cmd.getEnvironmentIds(),
                cmd.getGroupIds()
        ));
    }


    @CommandHandler
    public void handle(DeactivateUserCommand cmd)
    {
        apply(new UserDeactivatedEvent(
                cmd.getId()
        ));
    }

    @EventSourcingHandler
    private void on(UserCreatedEvent event)
    {
        this.id = event.getId();
        this.name = event.getName();
        this.address = event.getAddress();
        this.active = event.getActive();
        this.phoneNo = event.getPhoneNo();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.email = event.getEmail();
        this.environmentIds = event.getEnvironmentIds();
        this.groupIds = event.getGroupIds();
    }

    @EventSourcingHandler
    private void on(UserUpdatedEvent event)
    {
        this.name = event.getName();
        this.address = event.getAddress();
        this.active = event.getActive();
        this.phoneNo = event.getPhoneNo();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.email = event.getEmail();
        this.environmentIds = event.getEnvironmentIds();
        this.groupIds = event.getGroupIds();
    }

    @EventSourcingHandler
    private void on(UserDeactivatedEvent event){
        this.active=false;
    }
}
