package kopo.poly.service.impl;

import kopo.poly.dto.HospSearchDTO;
import kopo.poly.persistance.mongodb.IHospMapper;
import kopo.poly.service.IHospInfoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospInfoService implements IHospInfoService {

    private final IHospMapper hospMapper;

    @Override
    public List<HospSearchDTO> getHospInfo(HospSearchDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getHospInfo Start!");

        // MongoDB에 저장된 컬렉션 이름 (예시로 고정된 문자열 사용)
        String colNm = "SEOUL_HOSP_INFO";

        // 결과값
        List<HospSearchDTO> rList = new ArrayList<>();

        try {
            rList = hospMapper.getHospInfo(colNm, pDTO);
        } catch (Exception e) {
            log.error("Error occurred while fetching hospital information: " + e.getMessage());
            // 예외 처리 로직 추가 가능
        }

        log.info(this.getClass().getName() + ".getHospInfo End!");

        return rList;
    }
}
