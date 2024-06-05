package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {


    // 아이디 정보 가져오기
    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    int getUserLogin(UserInfoDTO pDTO) throws Exception;

    // 아이디 찾기
    String getEmailExists(UserInfoDTO pDTO) throws Exception;


    // 비밀번호 찾기
    String getPasswordExists(UserInfoDTO pDTO) throws Exception;

    String newPasswordProc(UserInfoDTO pDTO) throws Exception;

    String searchPasswordProc(UserInfoDTO pDTO)throws Exception;


    // 회원정보 수정 , 탈퇴
    int updateUserInfo(UserInfoDTO pDTO) throws Exception;

    void deleteUserInfo (UserInfoDTO pDTO) throws Exception;






}
