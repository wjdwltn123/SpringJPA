package kopo.poly.persistance.mongodb;

import kopo.poly.dto.HospSearchDTO;

import java.util.List;

public interface IHospMapper {

    List<HospSearchDTO> getHospInfo (String SEOUL_HOSP_INFO, HospSearchDTO pDTO) throws Exception;


}
