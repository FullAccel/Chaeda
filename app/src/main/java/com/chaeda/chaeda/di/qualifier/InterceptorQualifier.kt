package com.chaeda.chaeda.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Logger

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth