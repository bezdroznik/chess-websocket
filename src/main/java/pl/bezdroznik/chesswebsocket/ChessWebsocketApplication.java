package pl.bezdroznik.chesswebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;

@SpringBootApplication
public class ChessWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessWebsocketApplication.class, args);
	}

}
