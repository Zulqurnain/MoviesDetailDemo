package com.jutt.moviesdetaildemo.di

import android.content.Context
import com.jutt.moviesdetaildemo.BuildConfig
import com.jutt.moviesdetaildemo.data.network.ApiService
import com.jutt.moviesdetaildemo.data.persistence.AppDatabase
import com.jutt.moviesdetaildemo.data.persistence.daos.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

object NamedHilts {
    const val REPOSITORY_DISPATCHER: String = "REPOSITORY_DISPATCHER"
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    @Reusable
    @Provides
    fun provideAPIClientBuild(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().also { interceptor ->
            interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        })

    @Reusable
    @Provides
    fun provideAPIClientService(builder: OkHttpClient.Builder): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(builder.build())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Reusable
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    fun provideRepositoryDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Reusable
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context = context)

    @Reusable
    @Provides
    fun provideMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao()

}