package com.example.routes

import com.example.authentication.JwtService
import com.example.data.model.LoginRequest
import com.example.data.model.RegisterRequest
import com.example.data.model.SimpleResponse
import com.example.data.model.User
import com.example.repository.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val USERS = "/users"
const val REGISTER_REQUEST = "$USERS/registration"
const val LOGIN_REQUEST = "$USERS/login"
const val USER_REQUEST = "$USERS/userInfo"

fun Route.userRoutes(
    db: Repo,
    jwtService: JwtService,
    hashFunction: (String)->String
){
    get(USER_REQUEST){
        val email = try {
            call.request.queryParameters["email"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse("Query Parameter is Not Presented"))
            return@get
        }

        try {
            val user = db.findByUserEmail(email = email)
            call.respond(HttpStatusCode.OK, user!!)
        } catch (e: Exception){
            call.respond(
                HttpStatusCode.Conflict,
                SimpleResponse(e.message ?: "Some Problem Occurred!")
            )
        }
    }

    post(REGISTER_REQUEST) {
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest, SimpleResponse("Missing Some Fields"))
            return@post
        }

        try {
            val user = User(registerRequest.email,hashFunction(registerRequest.password),registerRequest.name, registerRequest.secondName, registerRequest.type)
            db.addUser(user)
            call.respond(HttpStatusCode.OK, jwtService.generateToken(user))
        }catch (e: Exception){
            call.respond(
                HttpStatusCode.Conflict,
                SimpleResponse(e.message ?: "Some Problem Occurred!")
            )
        }
    }

    post(LOGIN_REQUEST) {
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest, SimpleResponse("Missing Some Fields"))
            return@post
        }

        try {
            val user = db.findByUserEmail(loginRequest.email)

            if(user == null){
                call.respond(HttpStatusCode.BadRequest, SimpleResponse("Wrong Email Id"))
            } else {

                if(user.password == hashFunction(loginRequest.password)){
                    call.respond(
                        HttpStatusCode.OK,
                        SimpleResponse(jwtService.generateToken(user))
                    )
                } else{
                    call.respond(
                        HttpStatusCode.BadRequest,
                        SimpleResponse("Password Incorrect!")
                    )
                }
            }
        } catch (e: Exception){
            call.respond(
                HttpStatusCode.Conflict,
                SimpleResponse(e.message ?: "Some Problem Occurred!")
            )
        }
    }

}
