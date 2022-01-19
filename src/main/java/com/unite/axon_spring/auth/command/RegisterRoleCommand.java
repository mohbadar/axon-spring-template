package com.unite.axon_spring.auth.command;

import com.unite.axon_spring.auth.dto.RoleDTO;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@ToString
public class RegisterRoleCommand {
    private final RoleDTO roleDTO;

    @TargetAggregateIdentifier
    private String getAggregateIdentifier() {
        return (null != roleDTO.getId()) ? roleDTO.getId() +"" : roleDTO.getName();
    }
}
