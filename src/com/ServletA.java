package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Servlet implementation class ServletA
 */
@WebServlet("/ServletA")
@ServerEndpoint("/WSServerA/{user}")
public class ServletA extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletA() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("The Parameter are:"+request.getParameter("Message"));
		out.flush();
		out.close();
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request,response);//调用doGet()方法
	}
	
    private String currentUser;
	
	//连接打开时执行
	@OnOpen
	public void onOpen(@PathParam("user") String user, Session session) {
		currentUser = user;
		System.out.println(currentUser + "Connected ... " + session.getId());
	}

	 /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
	@OnMessage
	public String onMessage(String message, Session session) {
		System.out.println(currentUser + "：" + message);
		return currentUser + "：" + message;
	}

	/**
     * 连接关闭调用的方法
     */
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		
		System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}

	//连接错误时执行
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
}
