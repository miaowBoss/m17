package com.access.m17codetask.di

import com.access.m17codetask.repository.UserRepository
import dagger.Component

@Component(modules = [ServiceModule::class])
interface ServiceComponent {
    fun inject(userRepository: UserRepository)
}