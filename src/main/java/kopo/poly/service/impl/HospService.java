package kopo.poly.service.impl;

import kopo.poly.dto.HospDTO;
import kopo.poly.service.IHospService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class HospService implements IHospService {

    @Value("${Hosp.api.key}")
    private String Hosp_Key;

    @Value("${Hosp.url}")
    private String HospUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<HospDTO> getHospitalInfo(HospDTO pDTO) {

        try {
            log.info("HospService.getHospitalInfo() 호출");

            // API URL 설정
            String url = HospUrl + "?serviceKey=" + Hosp_Key;

            // URL 및 요청 헤더를 로그로 남김
            log.info("API 요청 URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, "application/json");
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

            // HTTP 요청 엔티티 생성
            HttpEntity<HospDTO> entity = new HttpEntity<>(pDTO, headers);

            // 요청 엔티티를 로그로 남김
            log.info("HTTP 요청 엔티티: {}", entity);

            // API 호출 및 응답 받기
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            log.info("API 응답 상태: {}", response.getStatusCode());
            log.info("API 응답 본문: {}", response.getBody());

            // 응답에서 아이템 리스트 추출하여 DTO로 변환
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
            List<HospDTO> hospList2 = items.stream().map(item -> {
                HospDTO hospDTO = HospDTO.builder()
                        .bizplcNm((String) item.get("bizplcNm"))
                        .bsnStateNm((String) item.get("bsnStateNm"))
                        .sickbdCnt((Integer) item.get("sickbdCnt"))
                        .treatSbjectCont((String) item.get("treatSbjectCont"))
                        .refineLotnoAddr((String) item.get("refineLotnoAddr"))
                        .build();
                log.info("병원 정보 DTO: {}", hospDTO);
                return hospDTO;
            }).collect(Collectors.toList());

            log.info("HospService.getHospitalInfo() 완료");
            return hospList2;
        } catch (Exception e) {
            log.error("HospService.getHospitalInfo() 오류 발생", e);
            throw new RuntimeException("병원 정보를 가져오는 중 오류가 발생했습니다.", e);
        }
    }
}
