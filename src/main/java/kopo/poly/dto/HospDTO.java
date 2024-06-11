package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public record HospDTO(


        //사업장명
        String bplcnm,

        //도로명주소
        String rdnwhladdr,

        //업태구분명
        String uptaenm,

        //전화번호
        String sitetel,

        //영업상태명
        String trdstatenm,

        // 병원 위치 x
        double  x,

        // 병원 위치 y
        double  y
) {
}
