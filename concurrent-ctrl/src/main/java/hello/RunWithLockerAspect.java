package hello;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.sap.cloud.platform.mobile.services.framework.locks.api.LockExpiredException;
import com.sap.cloud.platform.mobile.services.framework.locks.api.LockNotHeldException;
import com.sap.cloud.platform.mobile.services.framework.locks.api.NoSuchLockException;
import com.sap.cloud.platform.mobile.services.framework.locks.core.Locker;
import com.sap.cloud.platform.mobile.services.framework.locks.core.LockerFactory;

@Aspect
@Component
public class RunWithLockerAspect {

	private static final Logger log = LoggerFactory.getLogger(RunWithLockerAspect.class);
	private static final int MAX_RETRIES = 3;
	// the max expiry of a locker is 60 minutes;
	private static final int MAX_EXPIRY = 60;
	private static final int MIN_EXPIRY = 1;
	private static final int LOCKER_NAME_MAX_LENGTH = 256;

	@Autowired
	private LockerFactory lockerFactory;

	/**
	 * Advice by a join point where the executing method has a @RunWithLocker
	 * annotation.
	 */
	@Around("@annotation(runWithLocker)")
	public Object doAcquireLocker(ProceedingJoinPoint pjp, RunWithLocker runWithLocker) throws Throwable { // NOSONAR
		String lockerName = runWithLocker.value();
		if (lockerName.equals("")) {
			lockerName = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
			log.info("Use default locker name: {}", lockerName);
		} else {
			log.info("Use a user specified locker name: {}", lockerName);
		}
		if (lockerName.length() > LOCKER_NAME_MAX_LENGTH) {
			lockerName = lockerName.substring(0, LOCKER_NAME_MAX_LENGTH);
		}

		int expiryInMinute = runWithLocker.expiryInMinute();
		if (expiryInMinute < MIN_EXPIRY) {
			expiryInMinute = MIN_EXPIRY;
		} else if (expiryInMinute > MAX_EXPIRY) {
			expiryInMinute = MAX_EXPIRY;
		}
		log.info("expiryInMinute: {}", expiryInMinute);

		// acquire locker may sometimes fail with DuplicateKeyException (if two threads
		// are started at the same timestamp), so we will retry 3 attempts.
		Locker lock = lockerFactory.createLocker(lockerName, expiryInMinute * 60000);
		int numAttempts = 0;
		DuplicateKeyException lockFailureException;
		do {
			numAttempts++;
			lockFailureException = null;
			try {
				log.info("Acquire locker {} on {} attempt(s).", lockerName, numAttempts);
				lock.acquire();
				break;
			} catch (DuplicateKeyException ex) {
				log.warn("Cannot acquire locker. Caused by: {}", ex.toString());
				lockFailureException = ex;
			}
		} while (numAttempts < MAX_RETRIES);
		if (lockFailureException != null) {
			throw lockFailureException;
		}
		try {
			return pjp.proceed();
		} finally {
			// release locker
			try {
				log.info("Release locker {}", lockerName);
				lock.release();
			} catch (NoSuchLockException | LockExpiredException | LockNotHeldException e) {
				log.info("Error occurs when release locker. But it should not have any impacts. Caused by: {}",
						e.getMessage());
			}
		}
	}

}
