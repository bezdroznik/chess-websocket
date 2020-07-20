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
        stompClient.subscribe('/topic/chatStomp' , function(message) {
            showMessage(JSON.parse(message.body).value, JSON.parse(message.body).user);
        });
        stompClient.subscribe('/topic/board', function(gameState) {
            update(JSON.parse(gameState.body));
        });
    });
}

function showMessage(value, user){
    var newResponse = document.createElement('p');
    newResponse.appendChild(document.createTextNode(user));
    newResponse.appendChild(document.createTextNode(": "));
    newResponse.appendChild(document.createTextNode(value));
    var Response = document.getElementById('response');
    response.appendChild(newResponse);
}

function sendMessage () {
    var messageToSend = document.getElementById('messageToSend').value;
    var user = document.getElementById('user').value;
    stompClient.send("/app/chat", {}, JSON.stringify({'value': messageToSend, 'user' : user}) , JSON.stringify({}));
}

function promotion(gamestate) {
    document.getElementById("promotion").style.display = "";
    document.getElementById('queen').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'QY.png">';
    document.getElementById('rook').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'RY.png">';
    document.getElementById('bishop').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'BY.png">';
    document.getElementById('knight').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'NY.png">';
}

function selectTileForPromotion(name) {
    stompClient.send("/app/promotion", {}, JSON.stringify({'name':name}));
    document.getElementById("promotion").style.display = "none";
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

function joinGame() {
    stompClient.send("/app/joingame", {}, JSON.stringify({}));
}

function selectTile(name) {
    stompClient.send("/app/selectPiece", {}, JSON.stringify({'name':name}));
}

function update(gamestate) {
    if (gamestate.canCastling == "yes"){
        promotion(gamestate);
    } else {
        for (var row of gamestate.chessboard.tiles) {
            for(var tile of row) {
                if (tile.piece != null) {
                    document.getElementById(tile.name).innerHTML = '<img src="img/' + tile.piece.color.substring(0, 1) + tile.piece.symbol + '.png">';
                } else {
                    document.getElementById(tile.name).innerHTML = '';
                }
                updateBacklight(tile);
            }
        }
        if(gamestate.currentTile != null){
            document.getElementById(gamestate.currentTile.name).classList.add("selectedTile");
        }
    }
}

function updateBacklight (tile){
    if(document.getElementById(tile.name).classList.contains("selectedTile")){
        document.getElementById(tile.name).classList.remove("selectedTile");
    }
    if(document.getElementById(tile.name).classList.contains("availableTile")){
        document.getElementById(tile.name).classList.remove("availableTile");
    }

    if (tile.backlightSymbol == "Y"){
        document.getElementById(tile.name).classList.add("availableTile");
    }
}

