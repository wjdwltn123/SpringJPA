package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public record HospDTO(


        //사업장명 BIZPLC_NM
        String bizplcNm,

        //영업상태명 BSN_STATE_NM
        String bsnStateNm,

        //병상수(개) SICKBD_CNT
        Integer sickbdCnt,

        //진료과목내용 TREAT_SBJECT_CONT
        String treatSbjectCont,

        //소재지번주소 REFINE_LOTNO_ADDR
        String refineLotnoAddr
) {
}
