package com.unite.axon_spring;

import com.unite.axon_spring.iam.commandmodel.aggregate.EnvironmentAggregate;
import com.unite.axon_spring.iam.commandmodel.aggregate.RoleAggregate;
import com.unite.axon_spring.iam.coreapi.command.*;
import com.unite.axon_spring.iam.coreapi.event.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class RoleAggregateTest {
    

    private FixtureConfiguration<RoleAggregate> fixture;
    FixtureConfiguration<EnvironmentAggregate> environmentAggregateFixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(RoleAggregate.class);
        environmentAggregateFixture = new AggregateTestFixture<>(EnvironmentAggregate.class);
    }


    @Test
    void giveNoPriorActivity_whenCreateEnvironmentCommand_thenShouldPublishEnvironmentCreatedEvent() {
        environmentAggregateFixture.givenNoPriorActivity()
                .when(new CreateEnvironmentCommand("env-id","env-name","env-desc","desc","env",true ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EnvironmentCreatedEvent("env-id","env-name","env-desc","desc","env",true ));
    }


    @Test
    void giveNoPriorActivity_whenUpdateEnvironmentCommand_thenShouldPublishEnvironmentUpdatedEvent() {
        environmentAggregateFixture.givenNoPriorActivity()
                .when(new UpdateEnvironmentCommand("env-id","env-name","env-desc","desc","env",true ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new EnvironmentUpdatedEvent("env-id","env-name","env-desc","desc","env",true ));
    }

//    @Test
//    void giveNoPriorActivity_whenCreateRoleCommand_thenShouldPublishRoleCreatedEvent() {
//        fixture.givenNoPriorActivity()
//                .when(new CreateRoleCommand("role-id","role-name","role-desc",true,"env",null ))
//                .expectSuccessfulHandlerExecution()
//                .expectEvents(new RoleCreatedEvent("role-id","role-name","role-desc",true,"env",null));
//    }
//
//
//    @Test
//    void giveNoPriorActivity_whenUpdateRoleCommand_thenShouldPublishRoleUpdatedEvent() {
//        fixture.givenNoPriorActivity()
//                .when(new UpdateRoleCommand("role-id","role-name","role-desc",true,"env",null ))
//                .expectEvents(new RoleUpdatedEvent("role-id","role-name","role-desc",true,"env",null));
//    }
//
//    @Test
//    void giveNoPriorActivity_whenDeactivateRoleCommand_thenShouldPublishRoleDeactivatedEvent() {
//        fixture.givenNoPriorActivity()
//                .when(new DeactivateRoleCommand("role-id" ))
//                .expectEvents(new RoleDeactivatedEvent("role-id"));
//    }

}
