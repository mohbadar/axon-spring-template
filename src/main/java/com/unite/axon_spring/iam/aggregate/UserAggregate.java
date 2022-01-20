package com.unite.axon_spring.iam.aggregate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unite.axon_spring.iam.command.CreateUserCommand;
import com.unite.axon_spring.iam.command.DeactivateUserCommand;
import com.unite.axon_spring.iam.command.UpdateUserCommand;
import com.unite.axon_spring.iam.event.UserCreatedEvent;
import com.unite.axon_spring.iam.event.UserDeactivatedEvent;
import com.unite.axon_spring.iam.event.UserUpdatedEvent;
import com.unite.axon_spring.iam.model.Environment;
import com.unite.axon_spring.iam.model.Group;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
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
                cmd.isActive(),
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
                cmd.isActive(),
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
        this.active = event.isActive();
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
        this.active = event.isActive();
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
