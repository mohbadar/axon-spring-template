package com.unite.axon_spring.iam.restapi;

import com.unite.axon_spring.iam.common.audit.Auditable;
import com.unite.axon_spring.iam.commandmodel.model.Permission;
import com.unite.axon_spring.iam.coreapi.GetPermissionQuery;
import com.unite.axon_spring.iam.coreapi.GetPermissionsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/api/permissions")
public class PermissionController {

    private final QueryGateway queryGateway;

    @Autowired
    public PermissionController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Auditable
    @GetMapping()
    public ResponseEntity<List<Permission>> getPermissions() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(queryGateway.query(new GetPermissionsQuery(), ResponseTypes.multipleInstancesOf(Permission.class)).get());
    }


    @Auditable
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermission(@PathVariable(required = true) String id) throws ExecutionException, InterruptedException {
        CompletableFuture<Permission> future = queryGateway.query(new GetPermissionQuery(id), Permission.class);
        return ResponseEntity.ok(future.get());
    }
}
