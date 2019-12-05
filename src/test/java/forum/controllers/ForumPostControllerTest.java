package forum.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForumPostController.class)
@AutoConfigureMockMvc
public class ForumPostControllerTest {
	
	 @Autowired
	 private MockMvc mockMvc;

	 @Test
	 public void echoInputText() throws Exception {
		 String sampleText = "";
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(sampleText)
			       .contentType(MediaType.TEXT_PLAIN_VALUE)
			       .accept(MediaType.TEXT_PLAIN_VALUE))
		 		  .andDo(print())
			      .andExpect(status().isCreated())
			      .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
			      .andExpect(content().string(sampleText));
	 }
	 
	 @Test
	 public void echoInputTextEmptyString() throws Exception {
		 String sampleText = "";
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(sampleText)
			       .contentType(MediaType.TEXT_PLAIN_VALUE)
			       .accept(MediaType.TEXT_PLAIN_VALUE))
		 		  .andDo(print())
			      .andExpect(content().string(sampleText));
	 }
	 
	 @Test
	 public void echoInputTextWithSpecialCharacters() throws Exception {
		 String sampleText = "å∫π";
		 mockMvc.perform( MockMvcRequestBuilders
			       .post("/forumposts")
			       .content(sampleText)
			       .contentType("text/plain;charset=UTF-8")
			       .accept(MediaType.TEXT_PLAIN_VALUE))
		 		  .andDo(print())
		 		  .andExpect(status().isCreated())
				  .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				  .andExpect(content().string(sampleText));
	 }
	 
	 static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
