package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.HospDTO;
import kopo.poly.service.IHospService;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class HospService implements IHospService {

    @Value("${api.key}")
    private String apiKey;

    private Map<String, String> setProductInfo() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("accept", "application/xml;charset=UTF-8");
        requestHeader.put("Content-Type", "application/xml;charset=UTF-8");
        requestHeader.put("apikey", apiKey);

        return requestHeader;
    }

    public List<HospDTO> getProductApiList(String data) throws Exception {
        log.info(this.getClass().getName() + ".getProductApiList Start!");

        String apiUrl = IHospService.apiUrl;
        String apiParam = "?ServiceKey=" + apiKey + "&prdlstReportNo=" + "all"; // "all" 혹은 특정 조회 조건으로 수정

        log.info("apiParam : " + apiParam);

        String xml = NetworkUtil.get(apiUrl + apiParam, this.setProductInfo());

        log.info("xml : " + xml);

        // XML을 JSON으로 변환
        JSONObject jsonObject = XML.toJSONObject(xml);

        // JSONObject를 문자열로 변환
        String json = jsonObject.toString();

        log.info("json : " + json);

        // JSON 문자열을 Map으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> tMap = objectMapper.readValue(json.getBytes("UTF-8"), LinkedHashMap.class);

        log.info("tMap : " + tMap);

        // 'LOCALDATA_010101' 키의 데이터 가져오기
        Map<String, Object> localData = (Map<String, Object>) tMap.get("LOCALDATA_010101");

        // 'row' 데이터 가져오기 (List로 캐스팅)
        Object rowsObject = localData.get("row");

        List<HospDTO> hospitalList = new ArrayList<>();

        if (rowsObject instanceof List) {
            // 'row'가 List일 경우
            List<Map<String, Object>> rows = (List<Map<String, Object>>) rowsObject;

            // 모든 'row' 데이터 사용
            for (Map<String, Object> row : rows) {
                HospDTO rDTO = extractHospData(row);
                if (rDTO != null) {
                    hospitalList.add(rDTO);
                }
            }
        } else if (rowsObject instanceof Map) {
            // 'row'가 단일 객체일 경우
            Map<String, Object> row = (Map<String, Object>) rowsObject;
            HospDTO rDTO = extractHospData(row);
            if (rDTO != null) {
                hospitalList.add(rDTO);
            }
        } else {
            log.error("Unsupported data type for 'row': " + rowsObject.getClass().getName());
        }

        log.info(this.getClass().getName() + ".getHospitalList End!");

        return hospitalList;
    }

    // 병원 정보를 추출하는 메소드
    private HospDTO extractHospData(Map<String, Object> row) {
        String bplcnm = (String) row.get("BPLCNM");
        String rdnwhladdr = (String) row.get("RDNWHLADDR");
        String uptaenm = (String) row.get("UPTAENM");
        String sitetel = (String) row.get("SITETEL");
        String trdstatenm = (String) row.get("TRDSTATENM");

        // X와 Y 좌표를 Double로 변환하여 가져옴
        Double x = getDoubleValue(row.get("X"));
        Double y = getDoubleValue(row.get("Y"));

        log.info("bplcnm : " + bplcnm);
        log.info("rdnwhladdr : " + rdnwhladdr);
        log.info("uptaenm : " + uptaenm);
        log.info("sitetel : " + sitetel);
        log.info("trdstatenm : " + trdstatenm);
        log.info("x : " + x);
        log.info("y : " + y);

        // x와 y 값이 모두 null이 아닌 경우에만 데이터 저장
        if (x != null && y != null) {
            return HospDTO.builder()
                    .bplcnm(bplcnm)
                    .rdnwhladdr(rdnwhladdr)
                    .uptaenm(uptaenm)
                    .sitetel(sitetel)
                    .trdstatenm(trdstatenm)
                    .x(x)
                    .y(y)
                    .build();
        } else {
            log.warn("Skipping data because X or Y coordinate is null.");
            return null; // X나 Y 좌표 중 하나라도 null이면 데이터 저장하지 않음
        }
    }

    // Object를 Double로 변환하는 메소드
    private Double getDoubleValue(Object value) {
        if (value != null) {
            if (value instanceof Double) {
                return (Double) value;
            } else if (value instanceof String && !((String) value).isEmpty()) {
                try {
                    return Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    log.error("Failed to parse coordinate: " + e.getMessage());
                }
            }
        }
        return null;
    }
}
