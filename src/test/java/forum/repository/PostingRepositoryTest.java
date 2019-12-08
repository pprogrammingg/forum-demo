package forum.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import forum.document.Posting;
import forum.repositroy.PostingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostingRepositoryTest {
	
	@Autowired
	PostingRepository repository;

	Posting majorPost, minorPost, miscPost;

	@Before
	public void setUp() {

		repository.deleteAll();

		majorPost = repository.save(new Posting("major post!", new Date()));
		minorPost = repository.save(new Posting("minor post!", new Date()));
		miscPost = repository.save(new Posting("misc post!", new Date()));
	}
	
	@After 
	public void tearDown() {

		repository.deleteAll();
	}

	@Test
	public void setsIdOnSave() {

		assertThat(majorPost.id).isNotNull();
	}

	@Test
	public void findsByMessageBody() {

		List<Posting> result = repository.findByMessageBody("major post!");
		assertThat(result).hasSize(1).extracting("messageBody").contains("major post!");
	}
	
	@Test
	public void findsByNonExistentMessageBody() {

		List<Posting> result = repository.findByMessageBody("non-existent-message-body");
		assertThat(result).hasSize(0);
	}

	@Test
	public void findsAll() {

		List<Posting> result= repository.findAll();
		assertThat(result).hasSize(3);
	}
	
	@Test
	public void findsAllSortedByCreateDateTime() {
		
		List<Posting> result = repository.findAll(new Sort(Sort.Direction.DESC,"createDateTime"));
		assertThat(result.get(0).getCreateDateTime()).isAfter(result.get(1).getCreateDateTime());
	}
}
