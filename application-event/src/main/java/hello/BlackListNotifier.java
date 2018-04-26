package hello;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlackListNotifier implements ApplicationListener<BlackListEvent> {
	private String notificationAddress;

	public void setNotificationAddress(String notificationAddress) {
		this.notificationAddress = notificationAddress;
	}

	public BlackListNotifier() {
		notificationAddress = "blacklist@example.org";
	}

	public void onApplicationEvent(BlackListEvent event) {
		// notify appropriate parties via notificationAddress...
		System.out.println("Handle BlackListEvent ...");
	}
}
