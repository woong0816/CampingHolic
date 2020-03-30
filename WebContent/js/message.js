var serverIp = location.host;
var memberNum = "${memberNum}";
$(function(){
	if(nickName!="Guest"){
		connect();
		}else{
	}
		$("#sendmessagebtn").on("click",function(){
			var recv_nickname = $("#recv_name").val();
			var MSG_title = $("#msg_title").val();
			var MSG_content = $("#msg_content").val();
			sendMessage(memberNum,recv_nickname,MSG_title,MSG_content);

			
		});
		

}); 

var sock;
var stompClient;
function connect(){
	
//	alert(serverIp);
	//serverIp: ip주소+port번호
	sock = new SockJS("http://"+serverIp+"/CampingHolic/message/chat");
	
	//sock으로 연결하는게 아니라 sockjs 기반으로 stomp를 이용해서 연결
	stompClient = Stomp.over(sock);
	stompClient.connect({},function(){
	console.log("connect!!!!!!!!!!!!!!!");
		//서버로부터 메시지가 들어오면 처리 하겠다.......s 특정 url 메시지에 대해 구독
		//서버가 해당 url로 메시지를 보내면 메시지를 처리
		//stompClient.subscribe(destination, callback, headers)
		stompClient.subscribe("/message/topic/message/"+nickName,
				function(message){
			console.log("메세지!!!!!!!!!!!!!!!!!!"+message);
			
			alert("쪽지가 도착했습니다.");
			
		});
		
	});
};

function sendMessage(memberNum,recv_nickname,title,content){
//	alert(M_num);
	//alert("메시지 보내기");
	var targetid= memberNum;
	//메시지 보내기
	//	/client/greeting
		var msg = {
  				type : "message/chat",
  				sendUser : memberNum,
  				recvUser : recv_nickname,
  				title: title,
  				content:content
  		};	

	//var targetid = $("#targetUser").val();
	//alert(memberNum+"  : "+ memberNum+ " : "+ title+ "  :  " + content);
	stompClient.send("/message/client/greeting/"+memberNum+"/"+recv_nickname,{},JSON.stringify(msg));
	window.close();
};


