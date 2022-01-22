package com.unite.axon_spring.iam.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class BaseCommand(@TargetAggregateIdentifier val id: String)

data class CreateEnvironmentCommand(@TargetAggregateIdentifier val id: String, val slug: String, val name: String, val description: String, val secretKey: String, val active: Boolean)
data class UpdateEnvironmentCommand(@TargetAggregateIdentifier val id: String, val slug: String, val name: String, val description: String, val secretKey: String, val active: Boolean)
data class DeactivateEnvironmentCommand(@TargetAggregateIdentifier val id:String)

data class CreateRoleCommand(@TargetAggregateIdentifier val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val permissionIds:List<String>)
data class UpdateRoleCommand(@TargetAggregateIdentifier val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val permissionIds:List<String>)
data class DeactivateRoleCommand(@TargetAggregateIdentifier val id: String)


data class CreateGroupCommand(@TargetAggregateIdentifier val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val roleIds:List<String>)
data class UpdateGroupCommand(@TargetAggregateIdentifier val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val roleIds:List<String>)
data class DeactivateGroupCommand(@TargetAggregateIdentifier val id: String)


data class CreateUserCommand(@TargetAggregateIdentifier val id: String, val name: String, val address: String, val active: Boolean, val phoneNo: String, val username: String, val password:String, val email:String, val environmentIds:List<String>, val groupIds: List<String>)
data class UpdateUserCommand(@TargetAggregateIdentifier val id: String, val name: String, val address: String, val active: Boolean, val phoneNo: String, val username: String, val password:String, val email:String, val environmentIds:List<String>, val groupIds: List<String>)
data class DeactivateUserCommand(@TargetAggregateIdentifier val id: String)
