package me.smith_61.tunnel.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This is an annotation tag that is used to
 *  specify a method that receives an event.
 *  The types of events this method can accept
 *  is based off of the argument of the tagged
 *  method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
}
