package kopo.poly.repository;

import kopo.poly.repository.entity.HospEntity;
import kopo.poly.repository.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospRepository extends JpaRepository<HospEntity, Long> {


    // 별도의 메소드 선언 없이 JpaRepository에서 제공하는 기본적인 CRUD 메소드를 사용할 수 있습니다.

}
