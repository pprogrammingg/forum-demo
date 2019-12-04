package forum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ForumPostController {

	@PostMapping(path = "/forumposts", consumes = "text/plain;charset=UTF-8", produces = "text/plain;charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public String echoForumPost(@RequestBody String forumPost) {
	    return forumPost;
	}
}

