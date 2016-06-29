package cl.monsoon.access_order_targeting_cast.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Scope
@Documented
@Retention(SOURCE)
public @interface NotFinalForTesting {
}
