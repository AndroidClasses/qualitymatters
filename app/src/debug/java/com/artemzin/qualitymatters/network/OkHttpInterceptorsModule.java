package com.artemzin.qualitymatters.network;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static java.util.Collections.singletonList;

/**
 * Provides OkHttp interceptors for debug build.
 */
@Module
public class OkHttpInterceptorsModule {

    // Provided as separate dependency for Developer Settings to be able to change HTTP log level at runtime.
    @Provides @Singleton @NonNull
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Timber.d(message));
    }

    @Provides @OkHttpInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpInterceptors(@NonNull HttpLoggingInterceptor httpLoggingInterceptor) {
        return singletonList(httpLoggingInterceptor);
    }

    @Provides @OkHttpNetworkInterceptors @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return singletonList(new StethoInterceptor());
    }
}
