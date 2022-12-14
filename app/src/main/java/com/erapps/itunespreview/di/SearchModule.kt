package com.erapps.itunespreview.di

import com.erapps.itunespreview.data.api.service.ITunesApiService
import com.erapps.itunespreview.data.room.SuggestionsDao
import com.erapps.itunespreview.data.source.ISearchRepository
import com.erapps.itunespreview.data.source.SearchRepository
import com.erapps.itunespreview.data.source.local.search.ISearchLocalDataSource
import com.erapps.itunespreview.data.source.local.search.SearchLocalDataSource
import com.erapps.itunespreview.data.source.remote.search.ISearchRemoteDataSource
import com.erapps.itunespreview.data.source.remote.search.SearchRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(
        iTunesApiService: ITunesApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ISearchRemoteDataSource {
        return SearchRemoteDataSource(iTunesApiService, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideSearchLocalDataSource(
        suggestionsDao: SuggestionsDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ISearchLocalDataSource {
        return SearchLocalDataSource(suggestionsDao, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: ISearchRemoteDataSource,
        searchLocalDataSource: ISearchLocalDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): ISearchRepository {
        return SearchRepository(searchRemoteDataSource, searchLocalDataSource, defaultDispatcher)
    }
}