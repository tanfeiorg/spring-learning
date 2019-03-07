package hello;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that a method should not be executed concurrently. In a cluster
 * environment with multiple app instances, only one thread in one instance can
 * execute this method at the same time.
 * 
 * @author I062893
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RunWithLocker {

	/**
	 * The concurrent control is implemented by the database locker.
	 * Each @RunWithLocker annotation should have a locker name in its value.
	 * 
	 * The default value is "package+class+method".
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * A locker will expire in a given time period. If a locker is expired, then
	 * other threads can acquire the same locker even the locker has not been
	 * released.
	 *
	 * The default value is 1 minute. Max is 60.
	 * 
	 * @return
	 */
	int expiryInMinute() default 1;
}
