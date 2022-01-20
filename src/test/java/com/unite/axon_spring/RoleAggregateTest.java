package com.unite.axon_spring;

import com.unite.axon_spring.iam.aggregate.RoleAggregate;
import com.unite.axon_spring.iam.command.CreateRoleCommand;
import com.unite.axon_spring.iam.command.DeactivateRoleCommand;
import com.unite.axon_spring.iam.command.UpdateRoleCommand;
import com.unite.axon_spring.iam.event.RoleCreatedEvent;
import com.unite.axon_spring.iam.event.RoleDeactivatedEvent;
import com.unite.axon_spring.iam.event.RoleUpdatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoleAggregateTest {

    private static final String roleId = UUID.randomUUID().toString();
    private  static String name ="TEST_ROLE";
    private static String description ="TEST_DESCRIPTION";
    private static boolean active = true;
    private static String envSlug ="NSIA";
    private List<String> permissionIds = new ArrayList<>();


    private FixtureConfiguration<RoleAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(RoleAggregate.class);
    }

    @Test
    void giveNoPriorActivity_whenCreateRoleCommand_thenShouldPublishRoleCreatedEvent() {
        fixture.givenNoPriorActivity()
                .when(new CreateRoleCommand(roleId,name,description,active,envSlug,permissionIds ))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new RoleCreatedEvent(roleId,name,description,active,envSlug,permissionIds));
    }


    @Test
    void giveNoPriorActivity_whenUpdateRoleCommand_thenShouldPublishRoleUpdatedEvent() {
        fixture.givenNoPriorActivity()
                .when(new UpdateRoleCommand(roleId,name,description,active,envSlug,permissionIds ))
                .expectEvents(new RoleUpdatedEvent(roleId,name,description,active,envSlug,permissionIds));
    }

    @Test
    void giveNoPriorActivity_whenDeactivateRoleCommand_thenShouldPublishRoleDeactivatedEvent() {
        fixture.givenNoPriorActivity()
                .when(new DeactivateRoleCommand(roleId ))
                .expectEvents(new RoleDeactivatedEvent(roleId));
    }

}
