package com.example.phonebookwebservice;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.phonebookwebservice.models.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomersControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamCustomersReturnsOk() throws Exception {
		this.mockMvc.perform(get("/api/v1/customers")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void paramCustomersReturnsNotFoundOnDummyName() throws Exception {
		this.mockMvc.perform(get("/api/v1/customers/dummy"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void postCreatesCustomer() throws Exception {
		Set<String> phones = new HashSet<>();
		phones.add("123-456");
		Customer newCustomer = new Customer("Foo", phones);

		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/customers")
				.content(asJsonString(newCustomer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newCustomer.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.phones").exists());

		this.mockMvc.perform( MockMvcRequestBuilders.delete("/api/v1/customers/{id}", newCustomer.getName()));
	}

	@Test
	public void putUpdatesCustomer() throws Exception {
		String oldPhone = "123-456";
		Set<String> phones = new HashSet<>();
		phones.add(oldPhone);
		Customer newCustomer = new Customer("Bar", phones);

		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/customers")
				.content(asJsonString(newCustomer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		String newPhone = "90-60-90";

		this.mockMvc.perform(MockMvcRequestBuilders
				.put("/api/v1/customers/{id}", newCustomer.getName())
				.content(newPhone)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newCustomer.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.phones", containsInAnyOrder(oldPhone, newPhone)));

		this.mockMvc.perform( MockMvcRequestBuilders.delete("/api/v1/customers/{id}", newCustomer.getName()));
	}

	@Test
	public void deleteDeletesCustomer() throws Exception
	{
		String oldPhone = "123-456";
		Set<String> phones = new HashSet<>();
		phones.add(oldPhone);
		Customer newCustomer = new Customer("Baz", phones);

		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/customers")
				.content(asJsonString(newCustomer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		this.mockMvc.perform( MockMvcRequestBuilders.delete("/api/v1/customers/{id}", newCustomer.getName()))
				.andExpect(status().isAccepted());
	}


	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
