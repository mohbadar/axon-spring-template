package com.unite.axon_spring.iam.command;

import lombok.Data;

@Data
public class DeactivateRoleCommand extends BaseCommand<String> {
    public DeactivateRoleCommand(String id) {
        super(id);
    }
}
