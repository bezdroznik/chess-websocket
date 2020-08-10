package pl.bezdroznik.chesswebsocket.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String value;
    private String user;

    public ChatMessage(String value){
        this.value = value;
    }

    public ChatMessage(){

    }
}