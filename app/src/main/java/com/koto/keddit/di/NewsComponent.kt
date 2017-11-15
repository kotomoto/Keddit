package com.koto.keddit.di

import com.koto.keddit.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NewsModule::class, NetworkModule::class))
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}