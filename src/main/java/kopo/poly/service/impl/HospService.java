package kopo.poly.service.impl;

import kopo.poly.dto.HospDTO;
import kopo.poly.repository.HospRepository;
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

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${Hosp_key}")
    private String Hosp_Key;

    @Override
    public List<HospDTO> getHospitalInfo() {

        log.info("HospService.getHospitalInfo() 호출");

        // API 호출에 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", Hosp_Key);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 및 응답 받기
        ResponseEntity<Map> response = restTemplate.exchange(Hosp_Key, HttpMethod.GET, entity, Map.class);
        log.info("API 응답: {}", response);

        // 응답에서 아이템 리스트 추출하여 DTO로 변환
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");
        List<HospDTO> hospList = items.stream().map(item -> {
            HospDTO hospDTO = HospDTO.builder()
                    .bizplcNm((String) item.get("bizplcNm"))
                    .bsnStateNm((String) item.get("bsnStateNm"))
                    .sickbdCnt((Integer) item.get("SickbdCnt"))
                    .treatSbjectCont((String) item.get("Cont"))
                    .refineLotnoAddr((String) item.get("raddr"))
                    .build();
            log.info("병원 정보 DTO: {}", hospDTO);
            return hospDTO;
        }).collect(Collectors.toList());

        log.info("HospService.getHospitalInfo() 완료");
        return hospList;

    }
}