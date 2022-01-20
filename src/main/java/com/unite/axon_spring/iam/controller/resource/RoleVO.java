package com.unite.axon_spring.iam.controller.resource;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class RoleVO extends RepresentationModel<RoleVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private boolean active;
    @XmlElement
    private String envSlug;
    @XmlElement
    private List<String> permissionIds;

    public void setPermissions(List<String> permissionNames) {
        this.permissionIds = permissionNames;
    }
}
