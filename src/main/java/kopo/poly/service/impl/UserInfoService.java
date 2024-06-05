package kopo.poly.service.impl;

import jakarta.transaction.Transactional;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.UserInfoRespository;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service

public class UserInfoService implements IUserInfoService {

    private final UserInfoRespository userInfoRespository;

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo Start!");

        UserInfoDTO rDTO;

        String userId = CmmUtil.nvl(pDTO.userId());

        log.info("userId : " + userId);

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserId(userId);

        if (rEntity.isPresent()) {

            String userName = rEntity.get().getUserName();
            String email = EncryptUtil.decAES128CBC(rEntity.get().getEmail());
            String addr1 = rEntity.get().getAddr1();
            String addr2 = rEntity.get().getAddr2();
            String regDt = rEntity.get().getRegDt();

            log.info("userId : " + userId);
            log.info("userName : " + userName);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);
            log.info("regDt : " + regDt);

            rDTO = UserInfoDTO.builder()
                    .userId(userId)
                    .userName(userName)
                    .email(email)
                    .addr1(addr1)
                    .addr2(addr2)
                    .regDt(regDt)
                    .existsYn("Y")
                    .build();

        } else {

            rDTO = UserInfoDTO.builder().existsYn("N").build();

        }

        log.info(this.getClass().getName() + ".getUserInfo End!");

        return rDTO;
    }

    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        UserInfoDTO rDTO;

        String userId = CmmUtil.nvl(pDTO.userId()); // 아이디

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserId(userId);

        if (rEntity.isPresent()) {
            rDTO = UserInfoDTO.builder().existsYn("Y").build();
        } else {
            rDTO = UserInfoDTO.builder().existsYn("N").build();
        }
        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        int res = 0;
        String userId = CmmUtil.nvl(pDTO.userId());
        String userName = CmmUtil.nvl(pDTO.userName());
        String password = CmmUtil.nvl(pDTO.password());
        String email = CmmUtil.nvl(pDTO.email());
        String addr1 = CmmUtil.nvl(pDTO.addr1());
        String addr2 = CmmUtil.nvl(pDTO.addr2());

        log.info("pDTO : " + pDTO);

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserId(userId);

        if (rEntity.isPresent()) {
            res = 2;
        } else {

            DateUtil DataUtil;
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId).userName(userName)
                    .password(password)
                    .email(email)
                    .addr1(addr1).addr2(addr2)
                    .regId(userId).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .chgId(userId).chgDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .build();

            userInfoRespository.save(pEntity);

            rEntity = userInfoRespository.findByUserId(userId);

            if (rEntity.isPresent()) {
                res = 1;
            } else {
                res = 0;
            }

        }
        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return res;
    }

    @Override
    public int getUserLogin(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserLogin Start!");

        int res = 0;

        String userId = CmmUtil.nvl(pDTO.userId());
        String password = CmmUtil.nvl(pDTO.password());

        log.info("userId : " + userId);
        log.info("password: " + password);

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;
        }
        log.info(this.getClass().getName() + ".getUserLoginCheck End!");

        return res;
    }

    @Override
    public String getPasswordExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getPasswordExists Start!");

        String res = "";

        String userId = CmmUtil.nvl(pDTO.userId());
        String email = CmmUtil.nvl(pDTO.email());

        log.info("userId : " + userId);
        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserIdAndEmail(userId, email);

        if (rEntity.isPresent()) {

            res = rEntity.get().getPassword();
        }
        log.info("res : " + res);

        log.info(this.getClass().getName() + ".getPasswordExists End!");
        return res;
    }

    @Override
    public String newPasswordProc(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".newPasswordProc Start");

        String res = "";

        // DTO로부터 userId와 암호화된 password 추출
        String userId = CmmUtil.nvl(pDTO.userId());
        String password = CmmUtil.nvl(pDTO.password());

        // userId가 null이거나 비어 있는지 확인
        if (userId == null || userId.isEmpty()) {
            log.error("userId is null or empty");
            return "fail";
        }

        log.info("userId: " + userId);
        log.info("password: " + password);

        // UserInfoEntity 객체 생성
        UserInfoEntity entity = UserInfoEntity.builder()
                .userId(userId)
                .password(password)
                .build();

        // UserRepository의 save 메서드를 사용하여 엔티티 저장
        userInfoRespository.save(entity);

        res = "success"; // 비밀번호 저장 성공

        log.info(this.getClass().getName() + ".newPasswordProc End");

        return res;
    }


    @Override
    public String getEmailExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists Start!");

        String res = "";

        String userName = CmmUtil.nvl(pDTO.userName());
        String email = CmmUtil.nvl(pDTO.email());

        log.info("userName : " + userName);
        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserNameAndEmail(userName, email);

        if (rEntity.isPresent()) {

            res = rEntity.get().getUserId();
        }
        log.info("res : " + res);

        log.info(this.getClass().getName() + ".getEmailExists End!");

        return res;
    }


    @Override
    public String searchPasswordProc(UserInfoDTO pDTO) throws Exception {


        log.info(this.getClass().getName() + ".searchUserIdProc Start!");

        String res = "";

        String userId = CmmUtil.nvl(pDTO.userId());
        String email = CmmUtil.nvl(pDTO.email());

        log.info("pDTO userId : " + userId);
        log.info("pDTO email : " + email);

        Optional<UserInfoEntity> temp = userInfoRespository.findByUserId(userId);

        log.info(temp.get().getUserName());

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserIdAndEmail(pDTO.userId(), pDTO.email());

        log.info("rEntity : " + rEntity);

        if (rEntity.isPresent()) {
            UserInfoEntity userInfoEntity = rEntity.get();
            String password = userInfoEntity.getPassword();
            log.info("Found password: " + password); // userId가 성공적으로 조회되었을 때 로그 출력
            res = password;
        }

        log.info(this.getClass().getName() + ".searchUserIdProc End!");

        return res;
    }

    @Override
    public int updateUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateUserInfo Start!");

        String userId = pDTO.userId();

        int res = 0;

        Optional<UserInfoEntity> rEntity = userInfoRespository.findByUserId(userId);

        if (rEntity.isPresent()) {

            String userName = pDTO.userName();
            String email = EncryptUtil.encAES128CBC(pDTO.email());
            String addr1 = pDTO.addr1();
            String addr2 = pDTO.addr2();

            log.info("userId : " + userId);
            log.info("userName : " + userName);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);

            // 회원정보 DB에 저장
            userInfoRespository.updateUserInfo(userId, email, userName, addr1, addr2);

            res = 1;


        }
        log.info(this.getClass().getName() + ".updateUserInfo END!");
        return res;
    }

    @Override
    public void deleteUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteUserInfo Start!");

        String userId = pDTO.userId();

        log.info("userId : " + userId);


        // 데이터 수정하기
        userInfoRespository.deleteById(userId);

        log.info(this.getClass().getName() + ".deleteUserInfo End!");

    }

}


