package forum.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import forum.document.Posting;

public interface PostingRepository extends MongoRepository<Posting, String> {

	public Optional<Posting> findById(String id);
	public List<Posting> findByMessageBody(String messageBody);
	public List<Posting> findAll(Sort sort);
}