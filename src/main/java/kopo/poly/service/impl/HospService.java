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

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public HospDTO getProductApiList(String data) throws Exception {
        log.info(this.getClass().getName() + ".getProductApiList Start!");

        String apiUrl = IHospService.apiUrl;

        String apiParam = "?ServiceKey=" + apiKey + "&prdlstReportNo=" + data;

        log.info("apiParam : " + apiParam);

        String xml = NetworkUtil.get(apiUrl + apiParam, this.setProductInfo());

        log.info("xml : " + xml);

        // XML을 JSON으로 변환
        JSONObject jsonObject = XML.toJSONObject(xml);

        // JSONObject를 문자열로 변환
        String json = jsonObject.toString();

        log.info("json ; " + json);

        // ObjectMapper를 사용하여 JSON 문자열 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> tMap = objectMapper.readValue(json.getBytes("UTF-8"), LinkedHashMap.class);

        log.info("tMap : " + tMap);

        // body 안에 items가 있는지 확인
        Map<String, Object> responseBody = (Map<String, Object>) ((Map<String, Object>) tMap.get("LOCALDATA_010101")).get("row");

        //  병원정보 추출
        String bplcnm = new String(((String) responseBody.get("bplcnm")));
        String rdnwhladdr = new String(((String) responseBody.get("rdnwhladdr")));
        String uptaenm = new String(((String) responseBody.get("uptaenm")));
        String sitetel = new String(((String) responseBody.get("sitetel")));
        String trdstatenm = new String(((String) responseBody.get("trdstatenm")));

        log.info("bplcnm : " + bplcnm);
        log.info("rdnwhladdr : " + rdnwhladdr);
        log.info("uptaenm : " + uptaenm);
        log.info("sitetel : " + sitetel);
        log.info("trdstatenm : " + trdstatenm);

        HospDTO rDTO = HospDTO.builder()
                .bplcnm(bplcnm)
                .rdnwhladdr(rdnwhladdr)
                .uptaenm(uptaenm)
                .sitetel(sitetel)
                .trdstatenm(trdstatenm)
                .build();

        log.info(this.getClass().getName() + ".getProductApiList End!");

        return rDTO;
    }
}
