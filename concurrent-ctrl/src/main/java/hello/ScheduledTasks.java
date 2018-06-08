package hello;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final String LOCKER_NAME = "ScheduledTasks";

	@Scheduled(fixedRate = 20000, initialDelay = 1000)
	// @RunWithLocker(value = LOCKER_NAME, expiryInMinute = 61)
	@RunWithLocker(LOCKER_NAME)
	@Transactional
	public void reportCurrentTime() throws InterruptedException {
		log.info("A. current time 1: {}", dateFormat.format(new Date()));
		Thread.sleep(1000);
		log.info("A. current time 2: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("A. current time 3: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("A. current time 4: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
	}

	@Scheduled(fixedRate = 20000, initialDelay = 1000)
	// @RunWithLocker(value = LOCKER_NAME, expiryInMinute = 61)
	@RunWithLocker(LOCKER_NAME)
	@Transactional
	public void reportCurrentTime2() throws InterruptedException {
		log.info("B. current time 1: {}", dateFormat.format(new Date()));
		Thread.sleep(1000);
		log.info("B. current time 2: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("B. current time 3: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("B. current time 4: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
	}

	@Scheduled(fixedRate = 20000, initialDelay = 1000)
	// @RunWithLocker(value = LOCKER_NAME, expiryInMinute = 61)
	@RunWithLocker(LOCKER_NAME)
	@Transactional
	public void reportCurrentTime3() throws InterruptedException {
		log.info("C. current time 1: {}", dateFormat.format(new Date()));
		Thread.sleep(1000);
		log.info("C. current time 2: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("C. current time 3: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("C. current time 4: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
	}

	@Scheduled(fixedRate = 20000, initialDelay = 1000)
	// @RunWithLocker(value = LOCKER_NAME, expiryInMinute = 61)
	@RunWithLocker(LOCKER_NAME)
	@Transactional
	public void reportCurrentTime4() throws InterruptedException {
		log.info("D. current time 1: {}", dateFormat.format(new Date()));
		Thread.sleep(1000);
		log.info("D. current time 2: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("D. current time 3: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
		log.info("D. current time 4: {}", dateFormat.format(new Date()));
		// Thread.sleep(1000);
	}
}
