package kopo.poly.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HOSP_DATA")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity

public class HospEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long USER_ID;


    @Column(name = "name", length = 500, nullable = false)
    private String NAME;

    @Column(name = "address", nullable = false)
    private String ADDRESS;

    @Column(name = "type", nullable = false)
    private String TYPE;

    @Column(name = "tel", nullable = false)
    private String TEL;
    }

