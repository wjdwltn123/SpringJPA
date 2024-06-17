package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder

public record RedisDTO(


        //유저 이름
        String userId,

        //사업장명
        String bplcnm,

        //도로명주소
        String rdnwhladdr,

        //업태구분명
        String uptaenm,

        //전화번호
        String sitetel

        ) {
}
