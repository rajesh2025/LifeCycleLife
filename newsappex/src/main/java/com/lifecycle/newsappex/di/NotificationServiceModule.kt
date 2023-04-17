package com.lifecycle.newsappex.di

import com.lifecycle.newsappex.models.MessageService
import com.lifecycle.newsappex.models.NotificationService
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NotificationServiceModule() {

    @MessageQualifiers
    @Provides
    fun getMessageService(retryCount: Int): NotificationService {
        return MessageService(retryCount)
    }

//    @Named("email")
//    @Provides
//    fun getEmailService(emailService: EmailService): NotificationService {
//        return emailService
//    }
}