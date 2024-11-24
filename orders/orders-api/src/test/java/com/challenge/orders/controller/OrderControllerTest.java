package com.challenge.orders.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.orders.model.Order;
import com.challenge.orders.model.OrderList;
import com.challenge.orders.model.OrderStatus;
import com.challenge.orders.model.PatchOrder;
import com.challenge.orders.model.PostOrder;
import com.challenge.orders.repository.entity.OrderEntity;
import com.challenge.orders.repository.entity.OrderProductEntity;
import com.challenge.orders.repository.jpa.JpaOrderRepository;
import com.challenge.orders.test.util.TransactionUtil;
import com.challenge.catalogue.client.ProductClient;
import com.challenge.catalogue.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.challenge.core.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@ComponentScan(basePackages = "com.challenge.orders.test.util")
public class OrderControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private ProductClient productClient;

	@Autowired
    private ObjectMapper objectMapper;

	@Value("${local.timezone}")
	String tz;

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

	@Autowired
    private TransactionUtil transactionUtil;

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
		transactionUtil.transactionalRunnable(() -> repository.deleteAll());

	}

	@Test
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
		transactionUtil.transactionalRunnable(
			() -> {
				var entity = repository.getReferenceById(order.id());
				assertEquals(order.address(), entity.getAddress());
				assertEquals(order.status(), entity.getStatus());
				assertEquals(order.priceWithTax(), entity.getPrice());
				order.products().forEach(p -> assertTrue(
					entity.getProducts().stream().anyMatch(e ->
						e.getId().equals(p.id()) && e.getProductId().equals(p.productId()))
				));
			}

		);

	}

	@Test
	public void updateOrderWithValidAddressAndStatus() throws Exception {
		PatchOrder request = new PatchOrder(OrderStatus.DELIVERED, "new address");
		var oldOrder = createEntity();

		MvcResult result = mockMvc.perform(patch("/order/" + oldOrder.id())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

		Order order = objectMapper.readValue(jsonResponse, Order.class);

		validateOrder(order);

	}

	@Test
	public void updateOrderNotFound() throws Exception {
		PatchOrder request = new PatchOrder(OrderStatus.DELIVERED, "new address");

		mockMvc.perform(patch("/order/" + UUID.randomUUID())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isNotFound())
			.andReturn();

	}

	@Test
	public void createOrderWithInvalidProducts() throws Exception {
		PostOrder postOrder = new PostOrder("some address", List.of(nonExistingProductId));

		mockMvc.perform(post("/order")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(postOrder)))
			.andExpect(status().isNotFound())
			.andReturn();
	}

	@Test
	public void getAllOrders() throws Exception {
		var order1 = createEntity();
		var order2 = createEntity();

		var result = mockMvc.perform(get("/order"))
			.andExpect(status().isOk())
			.andReturn();

		var orderList = objectMapper.readValue(result.getResponse().getContentAsString(), OrderList.class);

		assertTrue(orderList.orders().containsAll(List.of(order1, order2)));
	}


	private Order createEntity(){

		return transactionUtil.transactionalSupplier(() -> {
			var entity = OrderEntity.builder()
			.address(TestUtil.randomString(10))
			.price(Math.random())
			.products(List.of(OrderProductEntity.builder()
				.productId(UUID.randomUUID())
				.build()
			))
			.build();

			return repository.save(entity).toModel(ZoneId.of(tz));
		});
	}

}
