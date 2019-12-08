package forum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import forum.document.Posting;

@RestController
@RequestMapping(path = "/")
public class ForumPostController {
	
	@PostMapping(path = "/forumposts")
	@ResponseStatus(HttpStatus.CREATED)
	public Posting echoForumPost(@RequestBody Posting posting) {
	    return posting;
	}
}

