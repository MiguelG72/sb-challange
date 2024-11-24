package com.challenge.orders.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderStatus;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import com.challenge.orders.repository.entity.OrderEntity;
import com.challenge.orders.repository.entity.OrderProductEntity;
import com.challenge.orders.repository.jpa.JpaOrderRepository;
import com.challenge.products.client.ProductClient;
import com.challenge.products.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.challenge.core.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private ProductClient productClient;

	@Autowired
    private ObjectMapper objectMapper;

	private UUID chocolateId = UUID.randomUUID();
	private UUID chipsId = UUID.randomUUID();
	private UUID nonExistingProductId = UUID.randomUUID();
	private Product chocolate = Product.builder()
				.id(chocolateId)
				.createdAt(LocalDateTime.now())
				.price(3.5)
				.name("chocolate")
				.build();

	private Product chips = Product.builder()
				.id(chipsId)
				.createdAt(LocalDateTime.now())
				.price(5.0)
				.name("chips")
				.build();

	@Autowired
	private JpaOrderRepository repository;

	@BeforeAll
	public void setUp(){
		when(productClient.getProduct(eq(chocolateId))).thenReturn(
			chocolate
		);

		when(productClient.getProduct(eq(chipsId))).thenReturn(
			chips
		);

		when(productClient.getProduct(eq(nonExistingProductId))).thenThrow(new FeignException.NotFound("Test Product Not Found",
				mock(Request.class), null, null
			)
		);
		repository.deleteAll();

	}

	@Test
	@Transactional
	public void createOrderWithTwoValidProducts() throws Exception {
		PostOrder postOrder = new PostOrder("some address", List.of(chocolateId, chipsId));

		MvcResult result = mockMvc.perform(post("/order")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(postOrder)))
			.andExpect(status().isCreated())
			.andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

		Order order = objectMapper.readValue(jsonResponse, Order.class);

		validateOrder(order);

	}

	private void validateOrder(Order order){

		var entity = repository.getReferenceById(order.id());
		assertEquals(order.address(), entity.getAddress());
		assertEquals(order.status(), entity.getStatus());
		assertEquals(order.priceWithTax(), entity.getPrice());
		order.products().forEach(p -> assertTrue(
			entity.getProducts().stream().anyMatch(e ->
				e.getId().equals(p.id()) && e.getProductId().equals(p.productId()))
		));

	}

	@Test
	@Transactional
	public void updateOrderWithValidAddressAndStatus() throws Exception {
		PatchOrder request = new PatchOrder(OrderStatus.DELIVERED, "new address");
		var oldEntity = createEntity(OrderStatus.PENDING);

		MvcResult result = mockMvc.perform(patch("/order/" + oldEntity.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

		Order order = objectMapper.readValue(jsonResponse, Order.class);

		validateOrder(order);

	}

	private OrderEntity createEntity(OrderStatus status){
		var entity = OrderEntity.builder()
			.address(TestUtil.randomString(10))
			.price(Math.random())
			.products(List.of(OrderProductEntity.builder()
				.productId(UUID.randomUUID())
				.build()
			))
			.status(status)
			.build();

		return repository.save(entity);
	}

}
