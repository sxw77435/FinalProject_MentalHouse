package com.uni.mental.member.controller;

import com.uni.mental.chating.EmailAPI;
import com.uni.mental.chating.WebSocketServer;
import com.uni.mental.member.model.dao.MemberDao;
import com.uni.mental.member.model.dto.MemberDto;
import com.uni.mental.member.model.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("login")
public class MemberController {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private EmailAPI emailAPI;




    @GetMapping("/loginpage")
    public String memberLoginForm(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "login/loginpage"; // 뷰 이름을 반환하여 해당 뷰로 이동하도록 합니다.
    }

    @GetMapping("/agreement")
    public String agreement(HttpSession session, Model model) {
        model.addAttribute("tempEmail", session.getAttribute("tempEmail"));
        model.addAttribute("tempNickname", session.getAttribute("tempNickname"));
        return "login/agreement";
    }
    @GetMapping("/enroll")
    public String enrollForm() {
        return "login/enroll";
    }

    @PostMapping("/enroll")
    public String enrollMember(@RequestParam("birthYear") String birthYear,
                               @RequestParam("birthMonth") String birthMonth,
                               @RequestParam("birthDate") String birthDate,
                               @RequestParam("post") String post,
                               @RequestParam("address1") String address1,
                               @RequestParam("address2") String address2,
                               @RequestParam("email1") String email1,
                               @RequestParam("email2") String email2,
                               MemberDto memberDto,
                               Model model) {
        // 주소 처리
        String fullAddress = post + "/" + address1 + "/" + address2;
        memberDto.setAdd(fullAddress);

        // 생일 타입 처리
        String birthString = birthYear + "-" + birthMonth + "-" + birthDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthString, formatter);
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthday);
        memberDto.setBir(sqlDate);

        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());
        memberDto.setPwd(encodedPassword);

        //email처리
        String email = email1 + email2;
        memberDto.setEmail(email);

        // 데이터 베이스에 저장
        memberDao.enrollMember(memberDto);

        model.addAttribute("memberDto", memberDto);

        System.out.println("memberDto: " + memberDto);

        return "redirect:/login/success";
    }

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // 랜덤하게 6자리 숫자 생성
        String verificationCode = generateRandomCode();

        String logoUrl = "https://i.postimg.cc/gjGJd3GH/Kakao-Talk-20240129-150246095.png";

        // 메일 보내기
        String subject = "인증번호";
        String body = "<html><head><style>"
                + ".notification { border: 2px solid orange; padding: 10px; text-align: center; }"
                + "img { width:80px; height:auto; margin-right:10px; }"
                + "</style></head>"
                + "<body>"
                + "<table style='width:100%;'><tr><td></td><td style='text-align:center;'>"
                + "<div class='notification'>"
                + "<img src='" + logoUrl + "' alt='Logo'><br>"
                + "<h2>Mental House</h2>"
                + "<p>인증번호: " + verificationCode + "</p>"
                + "</div>"
                + "</td><td></td></tr></table>"
                + "</body></html>";
        try {
            emailAPI.sendEmail(email, subject, body);
            return ResponseEntity.ok(verificationCode); // 返回生成的验证码
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증번호 보내 실패");
        }
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 生成0到9之间的随机数
            code.append(digit);
        }

        return code.toString();
    }




    @ResponseBody
    @RequestMapping("idCheck")
    public String idCheck(String id) {

        int count = memberService.idCheck(id);

        return String.valueOf(count);
    }

    @ResponseBody
    @RequestMapping("nicknameCheck")
    public String nicknameCheck(String nick) {

        int count = memberService.nicknameCheck(nick);

        return String.valueOf(count);
    }

    @GetMapping("/success")
    public String enrollsuccess() {
        return "login/success";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 멤버아이디와 비번 검증
        boolean isAuthenticated = memberService.authenticate(username, password);

        if (isAuthenticated) {
            // 검증성공후에 webSocket 연결
            webSocketServer.createWebSocketSession(username);

            return "redirect:/";
        } else {
            return "redirect:/login/error";
        }


    }

    // 카카오 로그인
    @PostMapping("/kakao-login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("email") String email,
                                        @RequestParam("nickname") String nickname,
                                        HttpSession session) {
        MemberDto existingMember = memberDao.findByEmail(email);
        if (existingMember != null) {
            // 사용자 인증 정보 설정
            List<SimpleGrantedAuthority> authorities = existingMember.getMemberRoleList().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority().getName()))
                    .collect(Collectors.toList());

            // 사용자 세션 정보 설정
            Authentication authentication = new UsernamePasswordAuthenticationToken(existingMember.getId(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 세션에 보안 컨텍스트 설정
            return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/main"));
        } else {
            // 새 사용자의 경우, 세션 속성 설정
            session.setAttribute("tempEmail", email);
            session.setAttribute("tempNickname", nickname);

            // agreement 페이지로 리다이렉트
            return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/login/agreement"));
        }
    }
//    @GetMapping("/kakao-logout")
//    public String kakaoLogout() {
//        return "redirect:/logout/logoutpage";
//    }

    // 추가 정보 입력 페이지 요청 처리
    @GetMapping("/additional-info")
    public String additionalInfoForm(HttpSession session, Model model) {
        model.addAttribute("email", session.getAttribute("tempEmail"));
        model.addAttribute("nickname", session.getAttribute("tempNickname"));

        System.out.println("tempEmail" + session.getAttribute("tempEmail"));
        System.out.println("tempNickname" + session.getAttribute("tempNickname"));

        return "login/additional-info";
    }

    @PostMapping("/submit-additional-info")
    public String submitAdditionalInfo(@ModelAttribute MemberDto memberDto, HttpSession session) {

        System.out.println("tempEmail" + session.getAttribute("tempEmail"));
        System.out.println("tempNickname" + session.getAttribute("tempNickname"));
        System.out.println("DTO:" + memberDto);

        String email = (String) session.getAttribute("tempEmail");
        String nickname = (String) session.getAttribute("tempNickname");
        // 사용자로부터 받은 비밀번호를 BCrypt 형식으로 인코딩
        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());
        // email 값을 MEMBER_ID로 사용
        memberDto.setId(email);
        memberDto.setEmail(email);
        memberDto.setNick(nickname);
        memberDto.setPwd(encodedPassword);

        System.out.println("Before saving to database: " + memberDto); // 로그 추가
        memberDao.enrollMember(memberDto);
        System.out.println("After saving to database: " + memberDto); // 로그 추가

        session.removeAttribute("tempEmail");
        session.removeAttribute("tempNickname");

        return "redirect:/login/success";
    }
}