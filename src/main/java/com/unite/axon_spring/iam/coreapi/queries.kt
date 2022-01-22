package com.unite.axon_spring.iam.coreapi

data class GetEnvironmentQuery(val id: String)
class GetEnvironmentsQuery()


data class GetGroupQuery(val id: String)
class GetGroupsQuery()

data class GetPermissionQuery(val id: String)
class GetPermissionsQuery()

data class GetRoleQuery(val id: String)
class GetRolesQuery()

data class GetUserByEmailQuery(val email: String)
class GetUsersQuery()
data class GetUserByEnvQuery(val envSlug:String)
data class GetUserByIdQuery(val id:String)
data class GetUserByUsernameQuery(val username:String)