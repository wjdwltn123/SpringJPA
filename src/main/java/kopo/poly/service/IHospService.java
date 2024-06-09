package kopo.poly.service;

import kopo.poly.dto.CommentDTO;
import kopo.poly.dto.HospDTO;

import java.util.List;

public interface IHospService {

    // API 호출을 위한 URL
    String apiUrl = "http://openapi.seoul.go.kr:8088/44486f5173776a6439306c43546443/xml/LOCALDATA_010101/1/5/";

    HospDTO getProductApiList(String data) throws Exception;


}
