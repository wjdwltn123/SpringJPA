package kopo.poly.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HOSP_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity

public class HospEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //사업장명 BIZPLC_NM
    @Column(name = "bizplc_nm")
    private String bizplcNm;

    //영업상태명 BSN_STATE_NM
    @Column(name = "bsn_state_nm")
    private String bsnStateNm;

    //병상수(개) SICKBD_CNT
    @Column(name = "sickbd_cnt")
    private Integer sickbdCnt;

    //진료과목내용 TREAT_SBJECT_CONT
    @Column(name = "treat_sbject_cont")
    private String treatSbjectCont;

    //소재지번주소 REFINE_LOTNO_ADDR
    @Column(name = "refine_lotno_addr")
    private String refineLotnoAddr;

    // 생성자, getter 및 setter 메서드 생략


}
