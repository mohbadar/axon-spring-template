package com.unite.axon_spring.iam.restapi.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class RolesListVO extends RepresentationModel<RolesListVO> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<RoleVO> roles = new ArrayList<RoleVO>();
}