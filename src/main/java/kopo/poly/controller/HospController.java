package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.HospDTO;
import kopo.poly.service.IHospService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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

        return "hospSearchMap";

    }

    /**
     * 병원 찾기 결과 화면으로 이동
     */
    @GetMapping(value = "searchHospResult")
    public String searchHospResult(@RequestParam(value = "data", required = false) String searchData, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".searchHospResult Start!");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        log.info("userId : " + userId);
        log.info("data : " + searchData);

        List<HospDTO> hospList;
        if (searchData != null && !searchData.isEmpty()) {
            // 검색어가 제공되면 해당 검색어로 병원을 필터링합니다.
            hospList = hospService.getProductApiList(searchData);
        } else {
            // 검색어가 제공되지 않으면 모든 병원을 가져옵니다.
            hospList = hospService.getProductApiList(searchData);
        }

        model.addAttribute("hospList", hospList); // 병원 정보 목록
        model.addAttribute("data", searchData); // 검색 데이터
        model.addAttribute("userId", userId); // 유저 아이디

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

    // 특정 병원을 예약하는 서비스
    @GetMapping("/hospCalender")
    public String hospCalender() {

        log.info(this.getClass().getName() + ".hosp/hospCalender Start!");

        log.info(this.getClass().getName() + ".hosp/hospCalender End!");

        return "hosp/hospCalender";
    }


    // 최근 사용자가 조회한 병원 정보

    @PostMapping("/saveHospInfo")
    public Map<String, Object> saveHospInfo(@RequestBody Map<String,  Object> hospInfo, @RequestParam String username) {
       Map<String, Object> response = new HashMap<>();




return  null;

    }



    }






