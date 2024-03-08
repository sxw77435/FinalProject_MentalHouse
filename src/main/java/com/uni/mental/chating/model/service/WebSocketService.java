package com.uni.mental.chating.model.service;

import com.uni.mental.chating.model.dao.WebSocketDao;
import com.uni.mental.chating.model.dto.ChatroomDto;
import com.uni.mental.chating.model.dto.WebSocketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WebSocketService {

    private final WebSocketDao webSocketDao;

    @Autowired
    public WebSocketService(WebSocketDao webSocketDao) {
        this.webSocketDao = webSocketDao;
    }
    public void insertChat(String senderId, String receiverId, String message,String senderemail,String receiveremail) {

        webSocketDao.insertChat(senderId, receiverId, message , senderemail,receiveremail);

    }

    public List<WebSocketDto> getChat(String senderId, String receiverId) {
        // 调用 DAO 层的方法从数据库中获取聊天记录
        return webSocketDao.getChat(senderId, receiverId);
    }


    public void createChatroom(ChatroomDto chatroomDto) {
        webSocketDao.createChatroom(chatroomDto);

    }

    public List<ChatroomDto> getAllChatrooms() {
        return webSocketDao.getAllChatrooms();
    }

    public void insertGroupMessage(int chatroomno,String senderId, String messageContent) {

        webSocketDao.insertGroupMessage(chatroomno ,senderId , messageContent);
    }

    public List<WebSocketDto> getChatByChatroomNo(int chatroomno) {
        return webSocketDao.getChatByChatroomNo(chatroomno);
    }


    public boolean confirmpwd(int roomNo, String password) {
        String storedPassword = webSocketDao.confirmpwd(roomNo);
        return password.equals(storedPassword);
    }
}
