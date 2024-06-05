package kopo.poly.controller;

import kopo.poly.dto.HospDTO;
import kopo.poly.service.IHospService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping(value = "/hosp")
@RequiredArgsConstructor
@Controller
public class HospController {

    private final IHospService hospService;


    // 경기도 병원 정보
    @GetMapping("/hospList")
    public String getHospInformation(Model model) {

        log.info(this.getClass().getName() + ".getHospInformation start");

        // 병원 정보 가져오기
        HospDTO pDTO = HospDTO.builder().build();  // 기본값으로 빈 DTO 사용
        List<HospDTO> hospList = hospService.getHospitalInfo(pDTO);

        log.info(this.getClass().getName() + ".getHospInformation end");

        // 모델에 병원 정보 추가
        model.addAttribute("hospList", hospList);

        // 병원 정보 페이지로 이동
        return "hospList";
    }



}
