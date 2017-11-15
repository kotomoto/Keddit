package com.koto.keddit

import android.app.Application
import com.koto.keddit.di.NewsComponent

class KedditApp : Application() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()

        newsComponent = DaggerNewsComponent.builder().build()
    }
}