package kopo.poly.service;

import kopo.poly.dto.CommentDTO;
import kopo.poly.dto.HospDTO;

import java.util.List;

public interface IHospService {

    List<HospDTO> getHospitalInfo(HospDTO pDTO);

    // API 호출을 위한 URL
    String HospUrl = "https://openapi.gg.go.kr/RecuperationHospital?";


}
