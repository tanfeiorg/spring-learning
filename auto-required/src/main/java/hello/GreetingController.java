package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Lazy
@RestController
public class GreetingController implements InitializingBean {

	@Autowired
	// use (required=false) to use an optional dependency.
	private IEmailService emailService;

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		emailService.sendEmail(name + "@sap.com", String.format(template, name));
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// Thie Assert is not executed. Because the emailService is required by default.
		Assert.notNull(emailService, "A bean definition of IEmailService is not found.");
	}
}