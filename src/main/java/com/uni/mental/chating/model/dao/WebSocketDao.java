package com.uni.mental.chating.model.dao;


import com.uni.mental.chating.model.dto.ChatroomDto;
import com.uni.mental.chating.model.dto.WebSocketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebSocketDao {
    void insertChat(String senderId, String receiverId, String message);

    List<WebSocketDto> getChat(String senderId, String receiverId);

    void createChatroom(ChatroomDto chatroomDto);

    List<ChatroomDto> getAllChatrooms();

    void insertGroupMessage(int chatroomno, String senderId, String messageContent);

    List<WebSocketDto> getChatByChatroomNo(int chatroomno);

    String confirmpwd(int roomNo);
}
