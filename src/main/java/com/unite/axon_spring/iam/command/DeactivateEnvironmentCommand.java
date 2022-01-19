package com.unite.axon_spring.iam.command;

public class DeactivateEnvironmentCommand extends BaseCommand<String> {
    public DeactivateEnvironmentCommand(String id) {
        super(id);
    }
}
