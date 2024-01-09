package com.byteteam.bluesense.core.di.module

import android.content.Context
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {
    @Provides
    @Singleton
    fun provideSignInClient(@ApplicationContext context: Context): SignInClient =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        client: SignInClient,
        dataStorePreference: DataStorePreference,
    ): GoogleSignInClient {
        return GoogleSignInClient(context, client, dataStorePreference)
    }
}