package com.omongole.fred.yomovieapp.di

import com.omongole.fred.yomovieapp.data.remote.services.ItunesApi
import com.omongole.fred.yomovieapp.data.remote.services.MovieApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [MovieApiModule::class, ItunesAppModule::class] )
interface AppComponent {
    fun getMovieApi(): MovieApi
    fun getItunesApi(): ItunesApi
}