package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.HospSearchDTO;
import kopo.poly.persistance.mongodb.IHospMapper;
import kopo.poly.service.IHospInfoService;
import kopo.poly.service.impl.HospInfoService;
import kopo.poly.service.impl.HospService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/hospMap")
public class HospInfoController {

    private final IHospInfoService hospInfoService;

    @GetMapping(value = "GetSearchMap")
    public String GetSearchMap() {
        log.info(this.getClass().getName() + "HospSearchMap start");

        log.info(this.getClass().getName() + "HospSearchMap end");

        return "hosp/hospSearchMap";

    }

    @ResponseBody
    @PostMapping(value = "HospSearchMap")
    public ResponseEntity<?> hospSearchMap(@RequestBody HospSearchDTO pDTO) {
        log.info(this.getClass().getName() + ".hospSearchMap Start!");

        List<HospSearchDTO> rList = new ArrayList<>();

        try {
            rList = hospInfoService.getHospInfo(pDTO);
        } catch (Exception e) {
            log.error("Error occurred while fetching hospital information: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred", null));
        }

        log.info(this.getClass().getName() + ".hospSearchMap End!");

        return ResponseEntity.ok(CommonResponse.of(HttpStatus.OK, "Success", rList));
    }
}

