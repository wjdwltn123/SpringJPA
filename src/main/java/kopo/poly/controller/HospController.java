package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.HospDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IHospService;
import kopo.poly.service.impl.UserInfoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/hosp")
@RequiredArgsConstructor
@Controller
public class HospController {

    private final IHospService hospService;
    private final UserInfoService userInfoService;

    /**
     * Hosp 검색 화면으로 이동
     */
    @GetMapping(value = "hospSearch")
    public String allergySearch() {
        log.info(this.getClass().getName() + ".allergy/hospSearch Start!");

        log.info(this.getClass().getName() + ".allergy/hospSearch End!");

        return "hosp/hospSearch";

    }

    /**
     * 병원 찾기 결과 화면으로 이동
     */
    @GetMapping(value = "searchHospResult")
    public String searchAllergyResult(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{

        log.info(this.getClass().getName() + ".searchAllergyResult Start!");

        String data = CmmUtil.nvl(request.getParameter("data"));


        log.info("data : " + data);

        HospDTO rDTO = Optional.ofNullable(hospService.getProductApiList(data)).orElseGet(() -> HospDTO.builder().build());

        model.addAttribute("rDTO", rDTO);       // 병원 정보 DTO
        model.addAttribute("data", data);       // 검색 데이터

        log.info("rDTO : " + rDTO);




        int res = 0;    // 비교용 변수

        log.info("res : " + res);
        model.addAttribute("res", res);

        log.info(this.getClass().getName() + ".searchHospResult End!");

        return "hosp/searchHospResult";
    }



    /**
     * 병원 검색 화면으로 이동
     */
    @GetMapping(value = "itemSearch")
    public String itemSearch() {

        log.info(this.getClass().getName() + ".allergy/itemSearch Start!");

        log.info(this.getClass().getName() + ".allergy/itemSearch End!");

        return "hosp/itemSearch";
    }

    /**
     * 병원 검색 결과 화면으로 이동
     */
    @GetMapping(value = "searchHospInfoResult")
    public String searchHospInfoResult() {

        log.info(this.getClass().getName() + ".allergy/searchItemResult Start!");

        log.info(this.getClass().getName() + ".allergy/searchItemResult End!");

        return "hosp/searchHospInfoResult";
    }




}
