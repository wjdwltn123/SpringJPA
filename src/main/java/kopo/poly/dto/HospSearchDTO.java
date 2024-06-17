package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder

public record HospSearchDTO(


        String id,
        String businessName,      // 사업장명
        String businessType,       // 업태구분명
        String lotAddress,         // 지번주소
        String coordinateX,    // 좌표정보(X)
        String coordinateY,        // 좌표정보(Y)
        String businessStatusName // 영업상태명


) {
}
