package com.unite.axon_spring.iam.command;

import lombok.Data;

public class DeactivateGroupCommand extends BaseCommand<String>{
    public DeactivateGroupCommand(String id) {
        super(id);
    }
}
