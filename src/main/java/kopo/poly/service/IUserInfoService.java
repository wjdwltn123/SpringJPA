package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserInfoService {

    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    int getUserLogin(UserInfoDTO pDTO) throws Exception;

    // 아이디 찾기
    String getEmailExists(UserInfoDTO pDTO) throws Exception;

    // 비밀번호 찾기
    String getPasswordExists(UserInfoDTO pDTO) throws Exception;

    String newPasswordProc(UserInfoDTO pDTO) throws Exception;

    String searchPasswordProc(UserInfoDTO pDTO)throws Exception;




}
