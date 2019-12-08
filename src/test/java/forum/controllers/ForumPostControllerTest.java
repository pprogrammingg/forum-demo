package forum.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import forum.document.Posting;
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
		 
		 Posting mockPostingResult = new Posting("someMsg", null, null, new Date());
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
		 
		 Posting mockPostingResult = new Posting("", null, null, new Date());
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
		 
		 Posting mockPostingResult = new Posting(messageBody, null, null, new Date());
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
	 
	 @Test
	 public void findAllPostings() throws Exception {
		 List<Posting> postings = Arrays.asList(
		            new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Yeye", "Miambo", new Date()));
		 
		 when(repository.findAll(any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings"))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Yeye")))
	            .andExpect(jsonPath("$[1].userLastName", is("Miambo")));
		 
		 verify(repository, times(1)).findAll(any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 @Test
	 public void findAllMatchingUserInfo() throws Exception {
		 List<Posting> postings = Arrays.asList(
		            new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Tia", "Koko", new Date()));
		 
		 when(repository.findByUserFirstNameAndUserLastName(any(String.class), any(String.class), any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings?userFirstName='Tia'&userLastName='Koko'"))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[1].userLastName", is("Koko")));
		 
		 verify(repository, times(1)).findByUserFirstNameAndUserLastName(any(String.class), any(String.class), any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 @Test
	 public void findAllWhenUserFirstNameProvidedButLastNameNull() throws Exception {
		 List<Posting> postings = Arrays.asList(
				 	new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Yeye", "Miambo", new Date()));
		 
		 when(repository.findAll(any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings").param("userFirstName", "Tia"))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Yeye")))
	            .andExpect(jsonPath("$[1].userLastName", is("Miambo")));
		 
		 verify(repository, times(1)).findAll(any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 @Test
	 public void findAllWhenUserLastNameProvidedButFirstNameNull() throws Exception {
		 List<Posting> postings = Arrays.asList(
				 	new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Yeye", "Miambo", new Date()));
		 
		 when(repository.findAll(any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings").param("userLastName", "Koko"))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Yeye")))
	            .andExpect(jsonPath("$[1].userLastName", is("Miambo")));
		 
		 verify(repository, times(1)).findAll(any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 @Test
	 public void findAllWhenUserFirstNameProvidedButLastNameEmpty() throws Exception {
		 List<Posting> postings = Arrays.asList(
				 	new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Yeye", "Miambo", new Date()));
		 
		 when(repository.findAll(any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings")
				.param("userFirstName", "Tia")
				.param("userLastName", ""))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Yeye")))
	            .andExpect(jsonPath("$[1].userLastName", is("Miambo")));
		 
		 verify(repository, times(1)).findAll(any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 @Test
	 public void findAllWhenUserLastNameProvidedButFirstNameEmpty() throws Exception {
		 List<Posting> postings = Arrays.asList(
				 	new Posting("hello world", "Tia", "Koko", new Date()),
		            new Posting("hello world Again", "Yeye", "Miambo", new Date()));
		 
		 when(repository.findAll(any(Sort.class))).thenReturn(postings);
		 
		 mockMvc.perform(get("/postings")
				.param("userFirstName", "")
				.param("userLastName", "Koko"))
		 		.andDo(print())
		 		.andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].messageBody", is("hello world")))
	            .andExpect(jsonPath("$[0].userFirstName", is("Tia")))
	            .andExpect(jsonPath("$[0].userLastName", is("Koko")))
	            .andExpect(jsonPath("$[1].messageBody", is("hello world Again")))
	            .andExpect(jsonPath("$[1].userFirstName", is("Yeye")))
	            .andExpect(jsonPath("$[1].userLastName", is("Miambo")));
		 
		 verify(repository, times(1)).findAll(any(Sort.class));
		 verifyNoMoreInteractions(repository);
	 }
	 
	 public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	 }
}
