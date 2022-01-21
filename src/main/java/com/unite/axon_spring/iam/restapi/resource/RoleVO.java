package com.unite.axon_spring.iam.restapi.resource;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@Data
public class RoleVO extends RepresentationModel<RoleVO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private List<String> permissionIds;

    public void setPermissions(List<String> permissionNames) {
        this.permissionIds = permissionNames;
    }
}
