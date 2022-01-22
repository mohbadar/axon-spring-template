package com.unite.axon_spring.iam.coreapi


data class BaseEvent(val id: String)

data class EnvironmentCreatedEvent( val id: String, val slug: String, val name: String, val description: String, val secretKey: String, val active: Boolean)
data class EnvironmentUpdatedEvent( val id: String, val slug: String, val name: String, val description: String, val secretKey: String, val active: Boolean)
data class EnvironmentDeactivatedEvent( val id:String)

data class RoleCreatedEvent( val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val permissionIds:List<String>)
data class RoleUpdatedEvent( val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val permissionIds:List<String>)
data class RoleDeactivatedEvent( val id: String)


data class GroupCreatedEvent( val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val roleIds:List<String>)
data class GroupUpdatedEvent( val id: String, val name: String, val description: String, val active: Boolean, val envSlug: String, val roleIds:List<String>)
data class GroupDeactivatedEvent( val id: String)


data class UserCreatedEvent( val id: String, val name: String, val address: String, val active: Boolean, val phoneNo: String, val username: String, val password:String, val email:String, val environmentIds:List<String>, val groupIds: List<String>)
data class UserUpdatedEvent( val id: String, val name: String, val address: String, val active: Boolean, val phoneNo: String, val username: String, val password:String, val email:String, val environmentIds:List<String>, val groupIds: List<String>)
data class UserDeactivatedEvent( val id: String)
