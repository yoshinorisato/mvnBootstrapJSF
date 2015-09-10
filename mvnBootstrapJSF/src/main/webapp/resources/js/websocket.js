
var socket;
$(document).ready(function(){
    socket = new WebSocket("ws://localhost:8080/mvnBootstrapJSF/endpoint");

    socket.onmessage = function(message){
        $('#messages').append(message.data + "<br/>");
    }

    $('#send').click(function(){
        socket.send(document.getElementById("form:messaget").value);
        $('#messaget').val('');
    })
});