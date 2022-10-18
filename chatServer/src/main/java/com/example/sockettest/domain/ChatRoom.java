package com.example.sockettest.domain;

import com.example.sockettest.persist.DialogRespository;
import com.example.sockettest.persist.entity.Dialog;
import com.example.sockettest.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ChatRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) throws IOException {
        if (chatMessage.getType().equals(ChatMessage.MessageType.LEAVE)) {
            if (sessions.contains(session)) {
                sessions.remove(session);
                session.close();
            }
            if (sessions.isEmpty()) {
                chatMessage.setMessage(chatMessage.getSender() + "님이 나갔습니다.");
            }
        }

        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}