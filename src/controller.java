

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class controller
 */
@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String name, recepient, subject, msgg;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		name= request.getParameter("name");
		recepient= request.getParameter("email_id");
		subject= request.getParameter("subject");
		msgg= request.getParameter("message");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio", "root", "");
			Statement stmt= con.createStatement();
			
			stmt.executeUpdate("insert into email value('"+name+"', '"+recepient+"', '"+subject+"','"+ msgg+"')");
			System.out.println("Done");
			sendmail();
			
			
			PrintWriter out= response.getWriter();
			/*out.println("<script src='js/popup1.js'></script>");
			out.println("<script src='js/popup2.js'></script>");
			out.println("<script>");
			out.println("$(document).ready(function(){");
			out.println("swal('Thanks For Your Interest')");
			out.println("});");
			out.println("</script>");*/
			
			out.println("<script>alert('Thanks For Your Interest')</script>");
			out.println("<script>window.location.href='portfolio.html'</script>");
			//RequestDispatcher rd= request.getRequestDispatcher("portfolio.html");
			//rd.include(request, response);
			//response.sendRedirect("portfolio.html");
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void sendmail() throws Exception{
		System.out.println("Preparing to sent email");
		Properties properties= new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myacc= "papulgsh1997@gmail.com";
		String mypass= "Helloworld1.";
		
		Session session= Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myacc,mypass);
			}
		});
		
		Message msg= message(session, myacc);
		Transport.send(msg);
		System.out.println("Message sent Successfully");
	}
	
	public Message message(Session session, String myacc) {
		Message message= new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myacc));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			String header= "reply: "+subject;
			message.setSubject(header);
			String text= "Dear "+name+",\nThank you for your interest on my profile.\n\nQuoted text:\n#Subject: "+subject+"\n#Message: "+msgg+"\n\nBest Regards,\nPapul Ghosh\n\n*** This is an automatically generated email. Please do not reply to this email.***";
			message.setText(text);
			return message;
		}
		catch (Exception e) {
			Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//doGet(request, response);
	}

}
