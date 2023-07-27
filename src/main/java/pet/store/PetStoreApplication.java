package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);

	/*the PetStoreApplication class serves as the entry point for 
	 * the Pet Store REST API application.  When you run this class,
	 * Spring Boot will initialize the application context, automatically 
	 * configure components, and start the embedded web server, allowing
	 * the application to handle incoming requests.
	 */
	}

}
