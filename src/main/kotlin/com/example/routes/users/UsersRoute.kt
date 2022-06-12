@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.example.routes.users

import io.ktor.server.locations.*

object UsersRoute {
    const val USERS = "/users"
    const val USER_LOGIN = "$USERS/login"
    const val USER_REGISTER = "$USERS/registration"
    const val USER_DETAILS = "$USERS/{id}"
    const val USER_UPDATE = USERS
    const val USERS_AVAILABLE = USERS

    @Location(USER_LOGIN)
    class UserLoginRoute

    @Location(USER_REGISTER)
    class UserRegisterRoute

    @Location(USER_DETAILS)
    data class UserDetailsRoute(val id: Int)

    @Location(USER_UPDATE)
    class UserUpdateRoute

    @Location(USERS_AVAILABLE)
    data class UsersAvailableRoute(val search: String?)
}