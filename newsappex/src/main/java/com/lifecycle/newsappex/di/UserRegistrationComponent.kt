package com.lifecycle.newsappex.di

import com.lifecycle.newsappex.ui.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component

@Component(modules = [UserRegistryModule::class, NotificationServiceModule::class])
interface UserRegistrationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance retryCount: Int): UserRegistrationComponent
    }
}