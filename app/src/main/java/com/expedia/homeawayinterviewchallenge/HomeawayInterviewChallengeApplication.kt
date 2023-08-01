package com.expedia.homeawayinterviewchallenge

import android.app.Application
import com.expedia.homeawayinterviewchallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HomeawayInterviewChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HomeawayInterviewChallengeApplication)
            modules(
                listOf(appModule)
            )
        }
    }
}