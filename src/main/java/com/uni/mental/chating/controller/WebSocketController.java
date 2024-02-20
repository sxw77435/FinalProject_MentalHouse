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

    @GetMapping("/toWebSocketDemo")
    public String toWebSocketDemo(@RequestParam("userId") String userId,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        // 从模型中获取接收者的 ID
        String receiverId = userId;

        // 获取两个用户之间的聊天记录
        List<WebSocketDto> messages = webSocketService.getChat(memberId, receiverId);

        // 将聊天记录添加到模型中，在页面上显示
        model.addAttribute("messages", messages);
        model.addAttribute("memberId", memberId);
        model.addAttribute("receiverId", receiverId);

        System.out.println("message: " + messages);
        System.out.println("memberId: " + memberId);
        System.out.println("receiverId: " + receiverId);

        return "chating/chat";
    }

    @PostMapping("/sendChatMessage")
    @ResponseBody
    public String insertChat(@RequestParam("senderId") String senderId,
                             @RequestParam("receiverId") String receiverId,
                             @RequestParam("message") String messageContentJson) {
        try {
            // 解析 JSON 字符串以获取消息内容
            JSONObject messageJson = new JSONObject(messageContentJson);
            String messageContent = messageJson.getString("message");

            System.out.println("messageContent"+messageContent);

            // 仅将消息内容保存到数据库
            webSocketService.insertChat(senderId, receiverId, messageContent);

            return "Message sent successfully";
        } catch (Exception e) {
            return "Error sending message";
        }
    }

    @GetMapping("/userlist")
    public String getUserList(Model model) {
        // 获取用户列表
        List<MemberDto> userList = memberService.getAllUsers();

        // 将用户列表添加到模型中
        model.addAttribute("userList", userList);
        System.out.println(userList);

        List<ChatroomDto> roomList = webSocketService.getAllChatrooms(); // 从服务层获取房间列表
        model.addAttribute("roomList", roomList); // 将房间列表添加到模型中
        System.out.println(roomList);


        return "/chating/userlist";
    }

    @PostMapping("/chatroom")
    @ResponseBody
    public String createChatroom(@RequestBody ChatroomDto chatroomDto) {
        try {
            System.out.println("Received chatroom name: " + chatroomDto.getChatroomname());
            System.out.println("Received chatroom password: " + chatroomDto.getChatroompwd());

            // 调用 WebSocketService 将聊天室信息插入数据库
            webSocketService.createChatroom(chatroomDto);
            return chatroomDto.getChatroomname();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to create chatroom";
        }
    }

    @GetMapping("/chatroom")
    public String chatroom(Model model, @RequestParam("chatroomno") int chatroomno){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();

        List<WebSocketDto> messages = webSocketService.getChatByChatroomNo(chatroomno);

        model.addAttribute("memberId", memberId);
        model.addAttribute("messages", messages); // 将获取到的聊天记录添加到模型中

        return "/chating/chatroom";
    }

    @PostMapping("/insertGroupMessage")
    @ResponseBody
    public String insertGroupMessage(@RequestParam("chatroomno") int chatroomno,
                                     @RequestParam("senderId") String senderId,
                                     @RequestParam("message") String messageContentJson) {
        try {
            // 解析 JSON 字符串以获取消息内容
            JSONObject messageJson = new JSONObject(messageContentJson);
            String messageContent = messageJson.getString("message");

            System.out.println("messageContent" + messageContent);

            // 仅将消息内容保存到数据库
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







