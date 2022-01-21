package com.unite.axon_spring.iam.eventhadler.projection;

import com.unite.axon_spring.iam.commandmodel.model.Permission;
import com.unite.axon_spring.iam.coreapi.query.GetPermissionQuery;
import com.unite.axon_spring.iam.coreapi.query.GetPermissionsQuery;
import com.unite.axon_spring.iam.eventhadler.repository.PermissionRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class PermissionProjection {

    @Autowired
    private PermissionRepository permissionRepository;

    @QueryHandler
    public List<Permission> getPermissions(GetPermissionsQuery query)
    {
        return permissionRepository.findAll();
    }

    @QueryHandler
    public Permission getPermission(GetPermissionQuery query)
    {
        return permissionRepository.findById(query.getId()).orElse(null);
    }
}
