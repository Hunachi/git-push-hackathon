package io.github.hunachi.gist.di

import io.github.hunachi.gist.GistLocalRepository
import io.github.hunachi.gist.GistRepository
import io.github.hunachi.gistlocal.GistDatabase
import io.github.hunachi.gistnetwork.GistClientFactory
import org.koin.dsl.module.module
import java.util.concurrent.Executors

val gistModule = module {

    single { GistRepository(get(), get()) }

    factory { GistLocalRepository(get(), Executors.newSingleThreadExecutor()) }

    single { GistClientFactory.gistClientInstance() }

    single { GistDatabase.getInstance(get()) }
}