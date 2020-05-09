package pl.bezdroznik.chesswebsocket.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewGameRequest {
    String string;

    public NewGameRequest() {
    }

    public NewGameRequest(String string) {
        this.string = string;
    }
}
