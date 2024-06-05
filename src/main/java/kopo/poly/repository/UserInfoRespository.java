package kopo.poly.repository;

import jakarta.transaction.Transactional;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRespository extends JpaRepository<UserInfoEntity, String> {

    Optional<UserInfoEntity> findByUserId(String userId);

    Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String password);

    Optional<UserInfoEntity> findByUserNameAndEmail(String userName, String email);

    Optional<UserInfoEntity> findByUserIdAndEmail(String userId, String email);
    
    /**
     * 유저 정보 업데이트
     *
     * @param userId 유저정보 PK
     */

    /**
     *
     * @Query 네이티브 쿼리 사용시 DB값은 변경되나 Entity값(캐시값)은 변경 x
     * @Modifying(clearAutomatically = true) 사용해서 객체 정보 초기화(Entity 정보 삭제) 후 캐시 갱신
     *
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE USER_INFO A SET A.email = ?2, A.USER_NAME = ?3, A.ADDR1 = ?4, A.ADDR2 = ?5 WHERE A.USER_ID = ?1",
            nativeQuery = true)
    int updateUserInfo(String userId, String email, String userName, String addr1, String add2);






}
