package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController implements InitializingBean {

	@Autowired(required = false)
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
		// If do not do assert, the NPE will not occur during the server initialization
		// phase
		// Assert.notNull(emailService, "A bean definition of IEmailService is not
		// found.");
	}
}