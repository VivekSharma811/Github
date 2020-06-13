package com.lightstone.github

import android.app.Application
import com.lightstone.github.model.db.UserDatabase
import com.lightstone.github.model.network.GithubApiService
import com.lightstone.github.model.network.datasource.GithubRepositoryDataSource
import com.lightstone.github.model.network.datasource.GithubRepositoryDataSourceImpl
import com.lightstone.github.model.network.datasource.UserDataSource
import com.lightstone.github.model.network.datasource.UserDataSourceImpl
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptor
import com.lightstone.github.model.network.interceptor.ConnectivityInterceptorImpl
import com.lightstone.github.model.repository.RepoRepository
import com.lightstone.github.model.repository.RepoRepositoryImpl
import com.lightstone.github.model.repository.UserRepository
import com.lightstone.github.model.repository.UserRepositoryImpl
import com.lightstone.github.model.response.GithubRepository
import com.lightstone.github.view.fragment.UserFragment
import com.lightstone.github.viewmodel.viewmodelfactory.ListViewModelFactory
import com.lightstone.github.viewmodel.viewmodelfactory.UserViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class GithubApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GithubApplication))

        bind() from singleton { UserDatabase(instance()) }
        bind() from singleton { instance<UserDatabase>().userDao() }
        bind() from singleton { instance<UserDatabase>().repoDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { GithubApiService(instance()) }

        bind<UserDataSource>() with singleton { UserDataSourceImpl(instance()) }
        bind<GithubRepositoryDataSource>() with singleton { GithubRepositoryDataSourceImpl(instance()) }

        bind<UserRepository>() with singleton { UserRepositoryImpl(instance(), instance()) }
        bind<RepoRepository>() with singleton { RepoRepositoryImpl(instance(), instance()) }

        bind() from provider { ListViewModelFactory(instance()) }
        bind() from provider { UserViewModelFactory(instance(), instance()) }
    }

}