var stompClient = null;
var connected = false;
connect()

function connect() {
    if (connected) {
        disconnect()
    }
    connected = true;
    var socket = new SockJS('/game');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected');
        stompClient.subscribe('/topic/board', function(gameState) {
            update(JSON.parse(gameState.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    connected = false;
    console.log("Disconnected");
}

function startGame() {
    stompClient.send("/app/newgame", {}, JSON.stringify({}));
}

function selectTile(name) {
    stompClient.send("/app/move", {}, JSON.stringify({'name':name}));
}

function update(gamestate) {
    for (var row of gamestate.chessboard.rows) {
        for(var tile of row) {
            if (tile.piece != null) {
                document.getElementById(tile.name).innerHTML = '<img src="img/' + tile.piece.color.substring(0, 1) + tile.piece.symbol + '.png">';
            } else {
                document.getElementById(tile.name).innerHTML = '';
            }
        }
    }
}