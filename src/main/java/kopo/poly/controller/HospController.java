package kopo.poly.controller;

import kopo.poly.repository.HospRepository;
import kopo.poly.service.IHospService;
import kopo.poly.service.impl.HospService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping(value = "/hosp")
@RequiredArgsConstructor
@Controller

public class HospController {

    @Value("${Hosp_url}")
    private String HospUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/retrieveData")
    public ResponseEntity<String> getHospInformation() {

        // API 호출을 위한 URL
        String HospUrl = "https://openapi.gg.go.kr/RecuperationHospital?";

        log.info(this.getClass().getName()+".retireData strat");

        // API 호출에 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("HospUrl", HospUrl); // API 키 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        // API 호출 및 응답 받기
        ResponseEntity<String> response = restTemplate.getForEntity(HospUrl, String.class);

        log.info(this.getClass().getName()+".retireData end");

        // API 응답 반환
        return response;
    }
}
