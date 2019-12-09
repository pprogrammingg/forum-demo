package forum.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import forum.document.Posting;

public interface PostingRepository extends MongoRepository<Posting, String> {

	public Optional<Posting> findById(String id);

	public List<Posting> findByMessageBody(String messageBody);

	public List<Posting> findByUserFirstNameAndUserLastName(String userFirstName, String userLastName, Sort sort);

	@Query("{'originalPostingId' : null}")
	public List<Posting> findOriginalPostingsOnly(Sort sort);

	/**
	 * 
	 * Finds comments for an original posting
	 * @param originalPostingId
	 * @param sort
	 * @return
	 */
	public List<Posting> findByOriginalPostingId(String originalPostingId, Sort sort);

	public List<Posting> findAll(Sort sort);
}