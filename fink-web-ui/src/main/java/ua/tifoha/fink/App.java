package ua.tifoha.fink;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.tifoha.fink.config.RootConfig;
import ua.tifoha.fink.entities.User;
import ua.tifoha.fink.repositories.UserRepository;

@Deprecated
public class App {
	public static void main(String[] args) throws JsonProcessingException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
		applicationContext.registerShutdownHook();
		UserRepository userRepository = applicationContext.getBean(UserRepository.class);
		User user = new User();
		user.setEnabled(true);
		user.setName("User");
		user.setPassword("pwd");
		System.out.println(user);
		user = userRepository.save(user);
		System.out.println(user);
		applicationContext.close();
		List<String> list = new ArrayList<>();
		for (String s : list) {
			if (s.isEmpty()) {
				System.out.println(s);
			}
		}
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.addMixIn(JobDetail.class, JobDetailsMixin.class);
//		JobDetail job = JobBuilder.newJob(Fink.App.HelloJob.class)
//								  .storeDurably(true)
//								  .withDescription("Dexctiopin")
//								  .usingJobData("phone", "+380971396134")
//								  .build();
//		String json = mapper.writeValueAsString(job);
//		System.out.println(json);
	}
}
