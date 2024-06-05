package kopo.poly.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity


public class NoticeSQLEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_seq")
    private Long noticeSeq;


    @Column(name = "title", length = 500, nullable = false)
    private String title;


    @Column(name = "contents", nullable = false)
    private String contents;


    @Column(name = "user_id", nullable = false)
    private String userId;


    @Column(name = "read_cnt", nullable = false)
    private Long readCnt;


    @Column(name = "reg_id", nullable = false)
    private String regId;


    @Column(name = "reg_dt", nullable = false)
    private String regDt;

    @Column(name = "chg_id")
    private String chgId;

    @Column(name = "chg_dt")
    private String chgDt;

    @Column(name = "user_name")
    private String userName;


}
