package com.tokopedia.play.di.key

import androidx.fragment.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by jegul on 27/05/20
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)