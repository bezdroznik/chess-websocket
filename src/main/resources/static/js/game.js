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
        stompClient.subscribe('/topic/games', function(availableGames) {
            buildTable(availableGames.body);
        });
        stompClient.subscribe('/topic/player', function(currentPlayer) {
            setLoginBarsVisibility(currentPlayer.body);
        });
        getCurrentPlayer();
        getGames();
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
    stompClient.send("/app/chat", {}, JSON.stringify({'value': messageToSend}) , JSON.stringify({}));
}

function promotion(gamestate) {
    document.getElementById("promotion").style.display = "";
    document.getElementById('queen').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'Q.png">';
    document.getElementById('rook').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'R.png">';
    document.getElementById('bishop').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'B.png">';
    document.getElementById('knight').innerHTML = '<img src="img/' + gamestate.promotionPawnTile.piece.color.substring(0, 1) + 'N.png">';
}

function selectTileForPromotion(name) {
    stompClient.send("/app/promotion", {}, JSON.stringify({'name':name}));
    document.getElementById("promotion").style.display = "none";
}

function setLoginBarsVisibility(currentPlayer){
console.log(currentPlayer);
console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    if (currentPlayer != null){
       document.getElementById("login").style.display = "none";
       document.getElementById("registration").style.display = "none";
       document.getElementById("logout").style.display = "";
    } else{
        document.getElementById("login").style.display = "";
        document.getElementById("registration").style.display = "";
        document.getElementById("logout").style.display = "none";
    }
}

function getCurrentPlayer() {
console.log(window.location.href);
console.log("getCurrentPlayer")
    if(window.location.href == 'http://localhost:8080/index.html'){
        stompClient.send("/app/getCurrentPlayer", {}, JSON.stringify({}));
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    connected = false;
    console.log("Disconnected");
}

function startGame() {
    stompClient.send("/app/newGame", {}, JSON.stringify({}));
}

function getGames() {
console.log(window.location.href);
console.log("getGames")
    if(window.location.href == 'http://localhost:8080/joingame.html'){
        stompClient.send("/app/getGames", {}, JSON.stringify({}));
    }
}

function joinGame(name) {
    stompClient.send("/app/joinGame", {}, JSON.stringify({'name':name}));
    console.log('joined ' + name);
}

function selectTile(name) {
    stompClient.send("/app/selectPiece", {}, JSON.stringify({'name':name}));
}

function update(gamestate) {
    if (gamestate.canPromote == true){
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

function buildTable(availableGames){
    availableGames = availableGames.replace("[", "");
    availableGames = availableGames.replace("]", "");
    availableGames = availableGames.replace(/\"/g, "");
    availableGames = availableGames.split(",");

	var table = document.getElementById('myTable');
    var counter = 0;
	for (var i = 0; i < availableGames.length; i++){
        counter++;

		var row = `<tr>
						<th>${counter}</th>
						<th><a href="game.html" onclick=joinGame(this.textContent)>${availableGames[i]}</a></th>
				  </tr>`
		table.innerHTML += row
	}

}
