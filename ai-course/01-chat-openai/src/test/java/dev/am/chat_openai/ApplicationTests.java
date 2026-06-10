package dev.am.chat_openai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("gemini")	// gemini, groq, deepseek, openrouter, dmr
class ApplicationTests {
	@Autowired
	MockMvcTester mockMvcTester;

	@Test
	void contextLoads() {
	}

	@Test
	void chat() throws Exception {
		MvcTestResult result = mockMvcTester.post().uri("/api/chat")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
							"prompt": "Tell me a java joke?"
						}
						""")
				.exchange();

		assertThat(result)
				.hasStatus(HttpStatus.OK)
				.bodyJson()
				.convertTo(ChatController.Output.class).satisfies(output -> {
					assertThat(output.response()).isNotBlank();
					IO.println("Response: " + output.response());
				});
	}

}
