package org.example.integration;

import org.example.dto.DogDto;
import org.example.entity.Gender;
import org.example.entity.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//public class DogApiIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void createAndListDogs() {
//        String base = "http://localhost:" + "8080" + "/api/dogs";
//
//        DogDto dto = new DogDto();
//        dto.name = "RexIntegration";
//        dto.breed = "Belgian Malinois";
//        dto.gender = Gender.MALE;
//        dto.currentStatus = Status.IN_SERVICE;
//
//        ResponseEntity<DogDto> createResp = restTemplate.postForEntity(base + "/", dto, DogDto.class);
//        assertEquals(HttpStatus.OK, createResp.getStatusCode());
//        assertNotNull(createResp.getBody());
//        assertNotNull(createResp.getBody().id, "Created dog should have an id");
//
//        ResponseEntity<String> listResp = restTemplate.getForEntity(base + "/dogs", String.class);
//        assertEquals(HttpStatus.OK, listResp.getStatusCode());
//        assertNotNull(listResp.getBody());
//        assertTrue(listResp.getBody().contains("RexIntegration"), "List response should contain created dog's name");
//    }
//}

