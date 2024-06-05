package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.service.impl.UserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.cache.CacheManager;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    // @RequriedArgsConstructor 를 통해 메모리에 올라간 서비스 객체를 Contoller에서 사용할수 있게 주입해줌
    private final IUserInfoService userInfoService;



    // 회원가입 화면으로 이동

    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm Start!");

        log.info(this.getClass().getName() + ".user/userRegForm End!");

        return "user/userRegForm";
    }

    /**
     * 회원 가입 전 아이디 중복체크하기 ( Ajax로 통해 입력한 아이디 정보 받음)
     */

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        // Builder 통한 값 저장
        UserInfoDTO pDTO = UserInfoDTO.builder().userId(userId).build();

        // 회원아이디를 통해 중복되는 아이디인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info(this.getClass().getName() + "getUserIdExists End!");

        return rDTO;

    }

    // 회원가입 로직 처리

    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".InsertUserInfo Start!");

        String msg;

        /*
         * 웹(회원 정보 입력 화면)에서 받는 정보를 String 변수에 저장 시작함
         *
         * 무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
         */

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String password = CmmUtil.nvl(request.getParameter("password"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
        String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

        /*
         * 웹(회원 정보 입력화면)에서 받는 정보를 String 변수에 저장 끝
         *
         * 무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
         */

        log.info("userId : " + userId);
        log.info("userName : " + userName);
        log.info("password : " + password);
        log.info("email : " + email);
        log.info("addr1 : " + addr1);
        log.info("addr2 : " + addr2);

        //웹(회원정보 입력화면)에서 받은 정보를 저장할 변수를 메모리에 올리기

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .userName(userName)
                .password(EncryptUtil.encHashSHA256(password))
                .email(EncryptUtil.encAES128CBC(email))
                .addr1(addr1)
                .addr2(addr2)
                .regId(userId)
                .chgId(userId)
                .build();

        int res = userInfoService.insertUserInfo(pDTO);

        log.info("회원가입 결과(res) : " + res);

        if (res == 1) {
            msg = "회원가입되었습니다.";

            //추후 회원가입이 입력화면에서 ajax를 활용한 아이디 중복, 이메일 중복을 체크하길 바람
        } else if (res == 2) {
            msg = "이미 가입된 아이디입니다.";
        } else {
            msg = "오류로 인해 회원가입이 실패하였습니다";
        }

        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return dto;
    }
    /*
     * 로그인을 위한 입력화면으로 이동
     */

    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login Start");

        log.info(this.getClass().getName() + ".user/login end");

        return "user/login";
    }
    /**
     * 로그인 처리 및 결과 알려주는 화면으로 이동
     */
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".loginProc Start!");

        String msg; // 로그인 결과에 대한 메시지를 전달할 변수

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(request.getParameter("password"));


        log.info("userId : " + userId);
        log.info("password : " + password);


        // 웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .password(EncryptUtil.encHashSHA256(password)).build();

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 usreInfoService 호춣하기
        int res = userInfoService.getUserLogin(pDTO);

        log.info("res : " + res);

        /*
         * 스프링에서 세션을 사용하기 위해서는 함수명의 파라미터에 HttpSession session 존재해야 한다.
         * 세션은 톰켓의 메모리에 저장되기 때문에 url마다 전달하는게 필요하지 않고,
         * 그냥 메모리에서 부르면 되기 때문에 jsp, controller에서 쉽게 불러서 쓸 수 있다.
         */

        if (res == 1) { // 로그인 성공

            /*
             * 세션에 회원아이디 저장하기, 추후 로그인여부를 체크하기 위해 세션에 값이 존재하는지 체크한다.
             * 일반적으로 세션에 저장되는 키는 대문자로 입력하며, 앞에 SS를 붙인다.
             *
             * Session 단어에서 SS를 가져온 것이다.
             */
            msg = "로그인이 성공했습니다.";
            session.setAttribute("SS_USER_ID", userId);

        } else {
            msg = "아이디와 비밀번호가 올바르지 않습니다.";

        }

        // 결과 메시지 전달하기
        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();
        log.info(this.getClass().getName() + "loginProc End!");

        return dto;
    }

    @GetMapping(value = "loginSuccess")
    public String loginSuccess() {
        log.info(this.getClass().getName() + ".user/loginsuccess start!");


        log.info(this.getClass().getName() + ".user/loginsuccess start!");

        return "user/loginSuccess";
    }
    @ResponseBody
    @PostMapping(value = "logout")
    public MsgDTO logout(HttpSession session) {
        log.info(this.getClass().getName() + ".logout start!");

        session.setAttribute("SS_USER_ID", "");
        session.removeAttribute("SS_USER_ID");

        MsgDTO dto = MsgDTO.builder().result(1).msg("로그아웃하였습니다.").build();

        log.info(this.getClass().getName() + ".logout end!");

        return dto;
    }



    @GetMapping(value = "/profile")                    // 개인정보 수정 페이지
    public String profile(HttpSession session ,ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".user/profile Start!");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        log.info("userId : " + userId);


        if (userId.length() > 0) {

            UserInfoDTO pDTO = UserInfoDTO.builder()
                    .userId(userId)
                    .build();

            UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO))
                    .orElseGet(() -> UserInfoDTO.builder().build());

            model.addAttribute("rDTO", rDTO);

        } else {

            return "/user/login";

        }

        log.info(this.getClass().getName() + ".user/profile End!");

        return  "user/profile";

    }

    @GetMapping(value = "/index")                    // main page
    public String index(HttpSession session ,ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".profile 함수 실행");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        model.addAttribute("userId", userId);

        return "user/profile";
    }

    @GetMapping(value = "SearchUserId")
    public String SearchUserId(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".user/SearchUserId1 start!");


        log.info(this.getClass().getName() + ".user/SearchUserId1 end!");

        return "/user/searchUserId";
    }

    @ResponseBody
    @GetMapping(value = "SearchUserIdProc")
    public MsgDTO SearchUserIdProc(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".user/SearchUserId2 start!");

        String msg;

        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("userName : " + userName);
        log.info("email : " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder().userName(userName).email(EncryptUtil.encAES128CBC(email)).build();

        String res = userInfoService.getEmailExists(pDTO);

        if ( !Objects.equals(res,"")) {
            msg = userName + "회원님의 아이디는" + res + "입니다.";
        } else {
            msg = "회원정보가 일치하지 않습니다.";
        }

        MsgDTO dto = MsgDTO.builder()
                .msg(msg)
                .build();

        log.info(this.getClass().getName() + ".user/SearchUserId2 end!");

        return dto;
    }


    @GetMapping(value = "newPassword")
    public String newPassword(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".user/SearchPassword start!");


        log.info(this.getClass().getName() + ".user/SearchPassword end!");

        return "/user/newPassword";

    }


    @ResponseBody
    @PostMapping(value = "searchPasswordProc")
    public MsgDTO searchPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".searchPasswordProc Start!");

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("userId: " + userId);
        log.info("email: " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .email(EncryptUtil.encAES128CBC(email))
                .build();

        String password = userInfoService.searchPasswordProc(pDTO);

        log.info("Retrieved password: " + password);

        MsgDTO dto;
        if (password != null && !password.isEmpty()) {
            session.setAttribute("NEW_PASSWORD_USER_ID", userId); // 성공적으로 비밀번호를 찾은 경우 세션에 아이디 저장
            dto = MsgDTO.builder().msg("success").build(); // 성공적으로 비밀번호를 찾은 경우
        } else {
            dto = MsgDTO.builder().msg("fail").build(); // 비밀번호를 찾지 못한 경우
        }

        log.info(this.getClass().getName() + ".searchPasswordProc End!");
        return dto;

       }

    @GetMapping(value = "newPasswordResult")
    public String newPasswordResult(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".user/newPasswordResult start!");


        log.info(this.getClass().getName() + ".user/newPasswordResult end!");

        return "/user/newPasswordResult";
    }


    @PostMapping(value = "/newPasswordProc", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public MsgDTO newPasswordProc(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".user/newPasswordProc Start!");

        String msg = "";
        MsgDTO dto;

        // 세션에서 userId 받아오기
        String userId = (String) session.getAttribute("NEW_PASSWORD_USER_ID");

        if (userId == null || userId.isEmpty()) {
            msg = "세션이 만료되었습니다. 다시 비밀번호 찾기를 진행해주세요.";
            dto = MsgDTO.builder().msg("fail").build(); // 비밀번호를 찾지 못한 경우
            return dto; // 클라이언트로 바로 JSON 반환
        }

        // 새 비밀번호 받아오기
        String password = CmmUtil.nvl(request.getParameter("password"));

        log.info("Received userId: " + userId);
        log.info("Received password: " + password);

        // 신규 비밀번호를 해시로 암호화
        String hashedPassword = EncryptUtil.encHashSHA256(password);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .password(hashedPassword)
                .build();

        // 서비스로 DTO 전달
        userInfoService.newPasswordProc(pDTO);

        // 비밀번호 재생성 후 세션 삭제
        session.removeAttribute("NEW_PASSWORD_USER_ID");

        msg = "비밀번호가 재설정되었습니다.";

        dto = MsgDTO.builder().msg("success").build(); // 성공적으로 비밀번호를 재설정한 경우
        log.info(this.getClass().getName() + ".user/newPasswordProc End!");

        log.info("dto : " + dto);
        return dto;
    }


    /**
     *
     * ##################################################################################
     *
     *                                      MyPage
     *
     * ##################################################################################
     */


    // 유저 정보 수정 페이지 이동
      @GetMapping(value = "profileEdit")
        public String myPageEdit(HttpSession session, ModelMap model) throws Exception {

            log.info(this.getClass().getName() + ".user/profileEdit Start!");

            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            log.info("userId : " + userId);


            if (userId.length() > 0) {

                UserInfoDTO pDTO = UserInfoDTO.builder()
                        .userId(userId)
                        .build();

                UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserInfo(pDTO))
                        .orElseGet(() -> UserInfoDTO.builder().build());

                model.addAttribute("rDTO", rDTO);

            } else {

                return "/user/login";

            }

            log.info(this.getClass().getName() + ".user/profileEdit End!");

            return  "user/profileEdit";

        }

       // 유저 정보 수정
        @ResponseBody
        @PostMapping(value = "updateUserInfo")
        public MsgDTO userInfoUpdate(HttpServletRequest request, HttpSession session) throws Exception {

            log.info(this.getClass().getName() + ".updateUserInfo Start!");

            String msg;

            String userId = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

            log.info("userId : " + userId);
            log.info("userName : " + userName);
            log.info("email : " + (email));
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);

            UserInfoDTO pDTO = UserInfoDTO.builder()
                    .userId(userId)
                    .userName(userName)
                    .email(email)
                    .addr1(addr1)
                    .addr2(addr2)
                    .build();

            int res = userInfoService.updateUserInfo(pDTO);

            log.info("호윈 정보 수정 결과(res) : " + res);

            if (res == 1) {
                msg = "회원 정보 수정되었습니다..";

            } else {

                msg = "오류로 인해 회원 정보 수정에 실패했습니다.";

            }

            MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

            log.info(this.getClass().getName() + ".updateUserInfo End!");

            return dto;
        }


      // 회원 탈퇴 페이지
        @GetMapping(value = "withdrawal")
        public String deleteUserInfo(HttpSession session) {

            log.info(this.getClass().getName() + ".user/withdrawal Start!");

            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            if(userId.length()>0){

            } else {
                return "user/login";
            }

            log.info(this.getClass().getName() + ".user/withdrawal End!");

            return "user/withdrawal";
        }


        /**
         * 유저 정보 삭제
         */

        @ResponseBody
        @PostMapping(value = "deleteUserInfo")
        public MsgDTO noticeDelete(HttpSession session) {

            log.info(this.getClass().getName() + ".deleteUserInfo Start!");

            String msg = ""; // 메시지 내용
            MsgDTO dto = null; // 결과 메시지 구조

            try {
                String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 글번호(PK)

                log.info("userId : " + userId);

                /*
                 * 값 전달은 반드시 DTO 객체를 이용해서 처리함 전달 받은 값을 DTO 객체에 넣는다.
                 */
                UserInfoDTO pDTO = UserInfoDTO.builder()
                        .userId(userId)
                        .build();

                // 게시글 삭제하기 DB
                userInfoService.deleteUserInfo(pDTO);

                session.invalidate();

                msg = "탈퇴하였습니다.";

            } catch (Exception e) {
                msg = "실패하였습니다. : " + e.getMessage();
                log.info(e.toString());
                e.printStackTrace();

            } finally {

                // 결과 메시지 전달하기
                dto = MsgDTO.builder().msg(msg).build();

                log.info(this.getClass().getName() + ".deleteUserInfo End!");

            }

            return dto;
        }



}

