package hello;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/v1/admin/**" })
public class SampleController {

	@RequestMapping
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("Hello World");
	}
}