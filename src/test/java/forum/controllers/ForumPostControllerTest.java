package forum.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import forum.document.Posting;

@RunWith(SpringRunner.class)
@WebMvcTest(ForumPostController.class)
public class ForumPostControllerTest {
	 @Autowired
	 private MockMvc mockMvc;
	 
	 private final String MEDIA_TYPE = "application/json;charset=UTF-8";
	
	 @Test
	 public void echoInputMessageBody() throws Exception {
		 String messageBody = "This is a message body!";
		 String postingJsonString = asJsonString(new Posting(messageBody));
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(postingJsonString)
			       .contentType(MediaType.APPLICATION_JSON)
			       .accept(MediaType.APPLICATION_JSON))
		 		  .andDo(print())
			      .andExpect(status().isCreated())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.messageBody").value(messageBody));
	 }
	 
	 @Test
	 public void echoInputMessageBodyEmptyString() throws Exception {
		 String messageBody = "";
		 String postingJsonString = asJsonString(new Posting(messageBody));
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(postingJsonString)
			       .contentType(MEDIA_TYPE)
			       .accept(MEDIA_TYPE))
		 		  .andDo(print())
			      .andExpect(content().string(postingJsonString));
	 }
	 
	 @Test
	 public void echoInputMessageBodyWithSpecialCharacters() throws Exception {
		 String messageBody = "å∫π";
		 String postingJsonString = asJsonString(new Posting(messageBody));
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(postingJsonString)
			       .contentType(MediaType.APPLICATION_JSON_VALUE)
			       .accept(MediaType.APPLICATION_JSON_VALUE))
		 		  .andDo(print())
		 		  .andExpect(status().isCreated())
				  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				  .andExpect(jsonPath("$.messageBody").value(messageBody));
	 }
	 
	 @Test
	 public void echoInputWrongPostingInput() throws Exception {
		 String someInput = "{\"blah\":\"bah\"}";
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(someInput)
			       .contentType(MediaType.APPLICATION_JSON_VALUE)
			       .accept(MediaType.APPLICATION_JSON_VALUE))
		 		.andExpect(jsonPath("$.messageBody", is(nullValue())));
	 }
	 
	 public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	 }
}
