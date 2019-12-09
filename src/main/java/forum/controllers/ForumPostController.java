package forum.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import forum.document.Posting;
import forum.exceptions.CreatePostingMalformedRequestException;
import forum.repositroy.PostingRepository;

@RestController
@RequestMapping(path = "/")
public class ForumPostController {
	
	@Autowired
	PostingRepository repository;
	
	@PostMapping(path = "/postings")
	@ResponseStatus(HttpStatus.CREATED)
	public Posting savePost(@RequestBody Posting posting) {
		
		if(posting.getMessageBody() == null) {
			throw new CreatePostingMalformedRequestException("Posting should have a non-null messageBody.");
		}
		
		if(posting.getCreateDateTime() == null) {
		    posting.setCreateDateTime(new Date());
		}
		
		return repository.save(posting);
	}
	
	// by default Get all returns posts sorted with createDateTime DESC order
	@GetMapping(path = "/postings")
	@ResponseStatus(HttpStatus.OK)
	public List<Posting> getAllPosts(@RequestParam(value="userFirstName", defaultValue="") String userFirstName, 
			  						 @RequestParam(value="userLastName", defaultValue="") String userLastName ) {
		
		if(userFirstName != null && !userFirstName.isEmpty() 
				&& userLastName != null && !userLastName.isEmpty()) {
			return repository.findByUserFirstNameAndUserLastName(userFirstName, userLastName, new Sort(Sort.Direction.DESC,"createDateTime"));
		}
		
	  return repository.findAll(new Sort(Sort.Direction.DESC,"createDateTime"));
	}
}

