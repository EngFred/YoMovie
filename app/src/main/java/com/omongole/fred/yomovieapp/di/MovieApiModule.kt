package com.omongole.fred.yomovieapp.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.omongole.fred.yomovieapp.data.remote.services.MovieApi
import com.omongole.fred.yomovieapp.data.repository.MovieRepositoryImpl
import com.omongole.fred.yomovieapp.data.repository.ShowsRepositoryImpl
import com.omongole.fred.yomovieapp.domain.repository.MovieRepository
import com.omongole.fred.yomovieapp.domain.repository.ShowsRepository
import com.omongole.fred.yomovieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieApiModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesMovieApiInstance() : MovieApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
        httpClient.readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(Constants.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun providesMovieRepository( api: MovieApi ) : MovieRepository = MovieRepositoryImpl( api )

    @Singleton
    @Provides
    fun providesShowsRepository( api: MovieApi) : ShowsRepository = ShowsRepositoryImpl( api )

    @Singleton
    @Provides
    fun provideDatastoreInstance(  @ApplicationContext context : Context  ) = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }

}