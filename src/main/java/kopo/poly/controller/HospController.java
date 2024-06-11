package kopo.poly.controller;

import ch.qos.logback.core.model.Model;
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
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/hosp")
@RequiredArgsConstructor
@Controller
public class HospController {

    private final IHospService hospService;

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
    public String searchHospResult(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{

        log.info(this.getClass().getName() + ".searchHospResult Start!");

        String data = CmmUtil.nvl(request.getParameter("data"));

        log.info("data : " + data);

        List<HospDTO> hospList = hospService.getProductApiList(data);
        model.addAttribute("hospList", hospList); // 병원 정보 목록
        model.addAttribute("data", data); // 검색 데이터

        log.info("hospList size: " + hospList.size());

        int res = 0; // 비교용 변수

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

        log.info(this.getClass().getName() + ".hosp/itemSearch Start!");

        log.info(this.getClass().getName() + ".hosp/itemSearch End!");

        return "hosp/itemSearch";
    }

    /**
     * 병원 검색 결과 화면으로 이동
     */
    @GetMapping(value = "searchHospInfoResult")
    public String searchHospInfoResult() {

        log.info(this.getClass().getName() + ".hosp/searchItemResult Start!");

        log.info(this.getClass().getName() + ".hosp/searchItemResult End!");

        return "hosp/searchHospInfoResult";
    }

    @GetMapping("/hospMap") // URL 경로 수정
    public String hospMap(HttpServletRequest request, ModelMap model) {
        log.info(this.getClass().getName() + ".hospMap Start!"); // 메소드 로그 수정

        String x = CmmUtil.nvl(request.getParameter("x"));
        String y = CmmUtil.nvl(request.getParameter("y"));

        log.info("x : " + x);
        log.info("y : " + y);

        model.addAttribute("x", x);
        model.addAttribute("y", y);

        log.info(this.getClass().getName() + ".hospMap End!"); // 메소드 로그 수정

        return "hosp/hospMap";
    }



}
