package com.lifecycle.newsappex.di

import com.lifecycle.newsappex.ui.MainActivity
import javax.inject.Inject

class UserRegistrationService @Inject constructor(
    private val userRepository: UserRepository,
    private val emailService: EmailService
) {

    fun registerUser(email: String, password: String) {
        userRepository.saveUser(email, password)
        emailService.send("zigba", "haba", "we we wedwf")
    }

}
