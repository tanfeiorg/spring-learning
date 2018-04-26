package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements ApplicationEventPublisherAware {

	private List<String> blackList;
	private ApplicationEventPublisher publisher;

	public void setBlackList(List<String> blackList) {
		this.blackList = blackList;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public EmailService() {
		blackList = new ArrayList<>();
		blackList.add("and.tan@sap.com");
	}

	public void sendEmail(String address, String text) {
		if (blackList.contains(address)) {
			BlackListEvent event = new BlackListEvent(this, address, text);
			publisher.publishEvent(event);
			return;
		}
		System.out.println("send email...");
	}
}