package com.example.javacursorrestprincipleshw7;

import com.example.javacursorrestprincipleshw7.entitys.Shop;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ShopControllerIntegrationTest {

    static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.25"))
            .withDatabaseName("test")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testCreateShop() throws Exception {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        shop.setCity("Test City");
        shop.setStreet("Test Street");
        shop.setHasWebsite(true);

        mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test Shop"))
                .andExpect(jsonPath("$.city").value("Test City"))
                .andExpect(jsonPath("$.street").value("Test Street"))
                .andExpect(jsonPath("$.hasWebsite").value(true));
    }

    @Test
    public void testDeleteShop() throws Exception {
        Shop shop = new Shop();
        shop.setName("Shop for Deletion");
        shop.setCity("Test City");
        shop.setStreet("Test Street");
        shop.setHasWebsite(false);

        String shopJson = mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andReturn().getResponse().getContentAsString();

        Shop createdShop = objectMapper.readValue(shopJson, Shop.class);
        Long shopId = createdShop.getId();

        mockMvc.perform(delete("/shops/{id}", shopId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/shops/{id}", shopId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllShops() throws Exception {
        mockMvc.perform(get("/shops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetShopById() throws Exception {
        Shop shop = new Shop();
        shop.setName("Test Shop 2");
        shop.setCity("Test City 2");
        shop.setStreet("Test Street 2");
        shop.setHasWebsite(true);

        String shopJson = mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andReturn().getResponse().getContentAsString();

        Shop createdShop = objectMapper.readValue(shopJson, Shop.class);
        Long shopId = createdShop.getId();

        mockMvc.perform(get("/shops/{id}", shopId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(shopId))
                .andExpect(jsonPath("$.name").value("Test Shop 2"))
                .andExpect(jsonPath("$.city").value("Test City 2"))
                .andExpect(jsonPath("$.street").value("Test Street 2"))
                .andExpect(jsonPath("$.hasWebsite").value(true));
    }

    @Test
    public void testUpdateShop() throws Exception {
        Shop shop = new Shop();
        shop.setName("Shop to Update");
        shop.setCity("Test City");
        shop.setStreet("Test Street");
        shop.setHasWebsite(false);

        String shopJson = mockMvc.perform(post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andReturn().getResponse().getContentAsString();

        Shop createdShop = objectMapper.readValue(shopJson, Shop.class);
        Long shopId = createdShop.getId();

        Shop updatedShop = new Shop();
        updatedShop.setName("Updated Shop");
        updatedShop.setCity("Updated City");
        updatedShop.setStreet("Updated Street");
        updatedShop.setHasWebsite(true);

        mockMvc.perform(put("/shops/{id}", shopId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedShop)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(shopId))
                .andExpect(jsonPath("$.name").value("Updated Shop"))
                .andExpect(jsonPath("$.city").value("Updated City"))
                .andExpect(jsonPath("$.street").value("Updated Street"))
                .andExpect(jsonPath("$.hasWebsite").value(true));
    }
}
