/*package forum.db.config;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackageClasses = PostingRepository.class)
@Configuration
public class MongoDBConfig {

	@Bean
	CommandLineRunner commandLineRunner(PostingRepository usersRepository) {
		return strings -> {
				usersRepository.save(new Users(new ObjectId(), "Alak", "Alestoon", "Toronto"));
				usersRepository.save(new Users(new ObjectId(), "Dolak", "Fandoghi", "Foshang"));
		};
	}
}*/
