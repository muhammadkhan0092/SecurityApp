package com.example.securityapp.domain.usecase

import com.example.securityapp.core.data.repository.ControllerUserRepository
import com.example.securityapp.utils.Result

class CreateControllerUserUseCase(
    private val repository: ControllerUserRepository
) {
    suspend operator fun invoke(email : String,password : String){
        val doesUserExist = repository.getUser(email)
        when(doesUserExist){
            is Result.Error -> {
                val createUserResult = repository.createUser(email,password)
                when(createUserResult){
                    is Result.Error<*> -> Result.Error("")
                    is Result.Success<*> -> Result.Success("")
                }
            }
            is Result.Success -> {
                val existingUser = doesUserExist.data
                when(existingUser?.password==password){
                    true -> Result.Success("")
                    false -> Result.Error("")
                }
            }
        }
    }
}