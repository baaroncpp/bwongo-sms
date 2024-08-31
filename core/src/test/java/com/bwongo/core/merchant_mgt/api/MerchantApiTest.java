package com.bwongo.core.merchant_mgt.api;

import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantRequestDto;
import com.bwongo.core.security.model.dto.AuthenticationRequestDto;
import com.bwongo.core.security.repository.TRefreshTokenRepository;
import com.bwongo.core.security.service.AuthenticationService;
import com.bwongo.core.security.service.RefreshTokenService;
import com.bwongo.core.user_mgt.models.dto.request.MerchantUserRequestDto;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.models.jpa.TUserGroup;
import com.bwongo.core.user_mgt.repository.TUserGroupRepository;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/26/24
 * @LocalTime 8:25 PM
 **/
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class MerchantApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TUserRepository userRepository;

    @Autowired
    private TUserGroupRepository userGroupRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TRefreshTokenRepository refreshTokenRepository;

    //@ServiceConnection
   @Container
   static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("bwongo_sms_db")
            .withUsername("aaron")
            .withPassword("aaron");

    @DynamicPropertySource
    static void dynamicConfiguration(DynamicPropertyRegistry registry) {
        //Postgres properties
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.flyway.enabled", () -> true); // Enable Flyway for testing
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
        registry.add("spring.flyway.schemas", () -> "core");
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    public static String asJsonString(final Object obj) {
        try{
            final var mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public String getAccessToken() throws Exception {
        return authenticationService.authenticate(new AuthenticationRequestDto("test@test.com", "admin"))
                .getAccessToken();
    }

    @Test
    void testAddMerchant() throws Exception {

        var userGroup = userGroupRepository.findTUserGroupByName("ADMIN_GROUP").get();
        var testUser = TUser.builder()
                .email("test@test.com")
                .password("$2y$12$R2gix.Nr/E4j9pmKVQHA4u/x.oyWf/wEPUBcQwxYerQSwqQXMcWZO")
                .accountLocked(Boolean.FALSE)
                .accountExpired(Boolean.FALSE)
                .approved(Boolean.TRUE)
                .credentialExpired(Boolean.FALSE)
                .firstName("testFirst")
                .secondName("testSecond")
                .userType(UserTypeEnum.ADMIN)
                .initialPasswordReset(Boolean.FALSE)
                .nonVerifiedEmail(Boolean.TRUE)
                .userGroup(userGroup)
                .build();

        userRepository.save(testUser);

        var merchantUser = new MerchantUserRequestDto(
               "testFirstName" ,
                "testSecondName" ,
                "test@bwongo.com",
                "BkTest@543"
        );

        var request = new MerchantRequestDto(
                1L,
                "test@bwongo.com",
                "256773055500",
                "TESTS MERCHANT",
                "test",
                "PERSONAL",
                merchantUser
        );

        var token = getAccessToken();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/merchant")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void getAllMerchants() throws Exception {

        var token = getAccessToken();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/merchant/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", String.valueOf(0))
                        .queryParam("size", String.valueOf(10)))
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
}