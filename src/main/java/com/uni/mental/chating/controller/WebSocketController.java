package com.uni.mental.chating.controller;


import com.uni.mental.chating.model.dao.WebSocketDao;
import com.uni.mental.chating.model.dto.ChatroomDto;
import com.uni.mental.chating.model.dto.WebSocketDto;
import com.uni.mental.chating.model.service.WebSocketService;
import com.uni.mental.member.model.dto.MemberDto;
import com.uni.mental.member.model.service.MemberService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chatbot")
public class WebSocketController {

    private final WebSocketService webSocketService;
    private final MemberService  memberService;
    private final WebSocketDao webSocketDao;

    @Autowired
    public WebSocketController(WebSocketService webSocketService,MemberService memberService, WebSocketDao webSocketDao) {
        this.webSocketService = webSocketService;
        this.memberService = memberService;
        this.webSocketDao = webSocketDao;
    }

    // 채팅 화면 나오기
    @GetMapping("/toWebSocketDemo")
    public String toWebSocketDemo(@RequestParam("userId") String userId,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        String receiverId = userId;

        List<WebSocketDto> messages = webSocketService.getChat(memberId, receiverId);

        model.addAttribute("messages", messages);
        model.addAttribute("memberId", memberId);
        model.addAttribute("receiverId", receiverId);

        System.out.println("message: " + messages);
        System.out.println("memberId: " + memberId);
        System.out.println("receiverId: " + receiverId);

        return "chating/chat";
    }

    // 채팅 내용이 데이터베이스에 저장
    @PostMapping("/sendChatMessage")
    @ResponseBody
    public String insertChat(@RequestParam("senderId") String senderId,
                             @RequestParam("receiverId") String receiverId,
                             @RequestParam("message") String messageContentJson) {
        try {

            JSONObject messageJson = new JSONObject(messageContentJson);
            String messageContent = messageJson.getString("message");

            System.out.println("messageContent"+messageContent);

            String senderemail = memberService.getEmailById(senderId);
            String receiveremail = memberService.getEmailById(receiverId);

            webSocketService.insertChat(senderId, receiverId, messageContent,senderemail,receiveremail );

            return "Message sent successfully";
        } catch (Exception e) {
            return "Error sending message";
        }
    }

    // 대화 유저들의 리스트 나오기
    @GetMapping("/userlist")
    public String getUserList(Model model) {

        List<MemberDto> userList = memberService.getAllUsers();


        model.addAttribute("userList", userList);
        System.out.println(userList);

        List<ChatroomDto> roomList = webSocketService.getAllChatrooms();
        model.addAttribute("roomList", roomList);
        System.out.println(roomList);


        return "/chating/userlist";
    }

    // 채팅방 추가
    @PostMapping("/chatroom")
    @ResponseBody
    public String createChatroom(@RequestBody ChatroomDto chatroomDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        chatroomDto.setChatroomowner(memberId);
        try {
            System.out.println("Received chatroom name: " + chatroomDto.getChatroomname());
            System.out.println("Received chatroom password: " + chatroomDto.getChatroompwd());

            chatroomDto.setChatroomname(chatroomDto.getChatroomname());
            chatroomDto.setChatroompwd(chatroomDto.getChatroompwd());
            chatroomDto.setChatroomowner(memberId);

            webSocketService.createChatroom(chatroomDto);
            return chatroomDto.getChatroomname();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to create chatroom";
        }
    }

    // 채팅 방 화면 나오기
    @GetMapping("/chatroom")
    public String chatroom(Model model, @RequestParam("chatroomno") int chatroomno){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        List<WebSocketDto> messages = webSocketService.getChatByChatroomNo(chatroomno);

        model.addAttribute("memberId", memberId);
        model.addAttribute("messages", messages);

        return "/chating/chatroom";
    }

    // 채팅방 기록들이 데이터 베이스 에 저장
    @PostMapping("/insertGroupMessage")
    @ResponseBody
    public String insertGroupMessage(@RequestParam("chatroomno") int chatroomno,
                                     @RequestParam("senderId") String senderId,
                                     @RequestParam("message") String messageContentJson) {
        try {
            JSONObject messageJson = new JSONObject(messageContentJson);
            String messageContent = messageJson.getString("message");

            System.out.println("messageContent" + messageContent);

            webSocketService.insertGroupMessage(chatroomno, senderId, messageContent);

            return "Message sent successfully";
        } catch (Exception e) {
            return "Error sending message";
        }
    }

    @GetMapping("/createchatroom")
    public String createchatroom(){
        return "/chating/createchatroom";
    }

    // 비번 일치하는지 확인
    @PostMapping("/confirmpwd")
    @ResponseBody
    public ValidationResponse confirmpwd(@RequestParam("roomNo") int roomNo, @RequestParam("password") String password) {
        boolean isValid = webSocketService.confirmpwd(roomNo, password);
        return new ValidationResponse(isValid);
    }

    static class ValidationResponse {
        private boolean valid;

        public ValidationResponse(boolean valid) {
            this.valid = valid;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}







