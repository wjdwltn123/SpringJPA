package kopo.poly.service;

import kopo.poly.dto.HospSearchDTO;

import java.util.List;

public interface IHospInfoService {
    List<HospSearchDTO> getHospInfo(HospSearchDTO pDTO) throws Exception;
}
