package com.unite.axon_spring.iam.common.init;

import com.unite.axon_spring.iam.commandmodel.model.Permission;
import com.unite.axon_spring.iam.eventhadler.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AuthInitializer {

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void init()
    {
        if(permissionRepository.findAll().size() <1)
            createInitialPermissions();
    }

    private void createInitialPermissions()
    {

        Permission viewPermssions = new Permission();
        viewPermssions.setName("VIEW_PERMISSION");
        viewPermssions.setDescription("View Permission");
        viewPermssions.setActive(true);
        permissionRepository.save(viewPermssions);

        Permission createRolePerm = new Permission();
        createRolePerm.setName("CREATE_ROLE");
        createRolePerm.setDescription("Create Role Permission");
        createRolePerm.setActive(true);
        permissionRepository.save(createRolePerm);

        Permission editRolePerm = new Permission();
        editRolePerm.setName("EDIT_ROLE");
        editRolePerm.setDescription("EDIT Role Permission");
        editRolePerm.setActive(true);
        permissionRepository.save(editRolePerm);

        Permission deleteRolePerm = new Permission();
        deleteRolePerm.setName("DELETE_ROLE");
        deleteRolePerm.setDescription("DELETE Role Permission");
        deleteRolePerm.setActive(true);
        permissionRepository.save(deleteRolePerm);

        Permission viewRolePerm = new Permission();
        viewRolePerm.setName("VIEW_ROLE");
        viewRolePerm.setDescription("VIEW Role Permission");
        viewRolePerm.setActive(true);
        permissionRepository.save(viewRolePerm);
    }
}
