package com.unite.axon_spring.iam.command;


public class DeactivateUserCommand extends BaseCommand<String>{
    public DeactivateUserCommand(String id) {
        super(id);
    }
}
