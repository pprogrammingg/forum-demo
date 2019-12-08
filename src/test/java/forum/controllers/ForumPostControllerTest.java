package forum.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import forum.document.Posting;
import forum.exceptions.CreatePostingMalformedRequestException;
import forum.repositroy.PostingRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ForumPostController.class)
public class ForumPostControllerTest {

	 @Autowired
	 private MockMvc mockMvc;

	 @MockBean
	 private PostingRepository repository;

	 @Test
	 public void savePosting() throws Exception {
		 
		 Posting newPostingRequest = new Posting("someMsg");
		 String newPostingRequestJsonStr = asJsonString(newPostingRequest);
		 
		 Posting mockPostingResult = new Posting("someMsg", new Date());
		 mockPostingResult.setId("123");
		 when(repository.save(any(Posting.class))).thenReturn(mockPostingResult);

		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/postings")
			       .content(newPostingRequestJsonStr)
			       .contentType(MediaType.APPLICATION_JSON)
			       .accept(MediaType.APPLICATION_JSON))
		 		  .andDo(print())
			      .andExpect(status().isCreated())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.messageBody").value("someMsg"))
		 		  .andExpect(jsonPath("$.id").value("123"))
		          .andExpect(jsonPath("$.createDateTime").isNotEmpty());
	 }
	 
	 @Test
	 public void savePostingWithMessageBodyEmptyString() throws Exception {
		 Posting newPostingRequest = new Posting("");
		 String newPostingRequestJsonStr = asJsonString(newPostingRequest);
		 
		 Posting mockPostingResult = new Posting("", new Date());
		 mockPostingResult.setId("123");
		 when(repository.save(any(Posting.class))).thenReturn(mockPostingResult);

		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/postings")
			       .content(newPostingRequestJsonStr)
			       .contentType(MediaType.APPLICATION_JSON)
			       .accept(MediaType.APPLICATION_JSON))
		 		  .andDo(print())
			      .andExpect(status().isCreated())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.messageBody").value(""))
		 		  .andExpect(jsonPath("$.id").value("123"))
		          .andExpect(jsonPath("$.createDateTime").isNotEmpty());
	 }
	 
	 @Test
	 public void savePostingMessageBodyWithSpecialCharacters() throws Exception {
		 String messageBody = "å∫π";
		 Posting newPostingRequest = new Posting(messageBody);
		 String newPostingRequestJsonStr = asJsonString(newPostingRequest);
		 
		 Posting mockPostingResult = new Posting(messageBody, new Date());
		 mockPostingResult.setId("123");
		 when(repository.save(any(Posting.class))).thenReturn(mockPostingResult);

		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/postings")
			       .content(newPostingRequestJsonStr)
			       .contentType(MediaType.APPLICATION_JSON)
			       .accept(MediaType.APPLICATION_JSON))
		 		  .andDo(print())
			      .andExpect(status().isCreated())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.messageBody").value(messageBody))
		 		  .andExpect(jsonPath("$.id").value("123"))
		          .andExpect(jsonPath("$.createDateTime").isNotEmpty());
	 }
	 
	 @Test
	 public void savePostingMalformedJsonInput() throws Exception {
		 
		 Posting newPostingWithNullMessageBody = new Posting(null);
		 String newPostingRequestJsonStr = asJsonString(newPostingWithNullMessageBody);

		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/postings")
			       .content(newPostingRequestJsonStr)
			       .contentType(MediaType.APPLICATION_JSON))
		 		  .andDo(print())
		 		  .andExpect(status().isBadRequest());
	 }
	 
	 public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	 }
}
