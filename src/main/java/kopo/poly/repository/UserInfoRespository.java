package kopo.poly.repository;

import kopo.poly.repository.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRespository extends JpaRepository<UserInfoEntity, String> {

    Optional<UserInfoEntity> findByUserId(String userId);

    Optional<UserInfoEntity> findByUserIdAndPassword(String userId, String password);

    Optional<UserInfoEntity> findByUserNameAndEmail(String userName, String email);

    Optional<UserInfoEntity> findByUserIdAndEmail(String userId, String email);





}
