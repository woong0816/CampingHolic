package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Repository
public class SocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> connectedUser;
	private Map<String, WebSocketSession> users;
	private final Logger logger = LogManager.getLogger(getClass());
	
	
	public SocketHandler() {
		connectedUser = new ArrayList<WebSocketSession>();
		users = new HashMap<String, WebSocketSession>();
		this.logger.info("create SocketHandler instance");
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//연결 생성
		connectedUser.add(session);
		System.out.println("연결 생성");
		//연결 될떄마다 세션 생성
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
			//연결 끊킴
		connectedUser.remove(session);
		System.out.println("연결 해제");
		
	}


	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("메시지 받음");
		System.out.println("message : " + message);

	}
	
	



	
}
