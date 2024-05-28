package kopo.poly.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Cacheable
@Entity

public class UserInfoEntity implements Serializable {

    @Id
    @Column(name ="USER_ID")
    private String userId;


    @Column(name = "USER_NAME", length = 500, nullable = false, updatable = false)
    private String userName;


    @Column(name = "PASSWORD", length = 100, nullable = false, updatable = false)
    private String password;


    @Column(name = "email", nullable = false , updatable = false)
    private String email;


    @Column(name ="addr1", nullable = false, updatable = false)
    private String addr1;


    @Column(name ="addr2", nullable = false, updatable = false)
    private String addr2;


    @Column(name ="reg_id", updatable = false)
    private String regId;


    @Column(name ="reg_dt", updatable = false)
    private String regDt;

    @Column(name = "chg_id", updatable = false)
    private String chgId;

    @Column(name = "chg_dt", updatable = false)
    private String chgDt;







}
