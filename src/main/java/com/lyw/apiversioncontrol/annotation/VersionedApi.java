package com.lyw.apiversioncontrol.annotation;

import com.lyw.apiversioncontrol.domain.Version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionedApi {

    String name();

    String from() default Version.MIN_VERSION;

    String to() default Version.MAX_VERSION;

}
