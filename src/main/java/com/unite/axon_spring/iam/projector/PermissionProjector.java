package com.unite.axon_spring.iam.projector;

import com.unite.axon_spring.iam.model.Permission;
import com.unite.axon_spring.iam.query.GetPermissionQuery;
import com.unite.axon_spring.iam.query.GetPermissionsQuery;
import com.unite.axon_spring.iam.repository.PermissionRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionProjector {

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
