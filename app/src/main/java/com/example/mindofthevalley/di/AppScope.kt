package com.example.mindofthevalley.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

/**
 * not entirely sure if this is required for the complexity of this app, but adding anyway for
 * instance-use consistency and memory balancing
 **/