package LoginByUsingFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/welcome")
public class LoginFilter extends HttpFilter implements Filter {
     
	
	Connection con;
	public void init(FilterConfig fc)
	{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","vinayak");

		}catch(Exception e)
		{
		System.err.println(e);
		}
		}
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try{
			con.close();
		}catch(Exception e)
		{
		System.err.println(e);
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		try{
			String s1=request.getParameter("uname");
			String s2=request.getParameter("pword");
			PreparedStatement pstmt=con.prepareStatement("select * from uinfo where uname=? and pword=?");
					pstmt.setString(1, s1);
					pstmt.setString(2, s2);
					ResultSet rs=pstmt.executeQuery();
					if(rs.next())
					{
						chain.doFilter(request, response);
					}
					else
					{
					PrintWriter pw=response.getWriter();
					pw.println("<html><body bgcolor=yellow text=red><h1>");
					pw.println("Invalid Username/Password");
					pw.println("</h1></body></html>");
					}
					}catch(Exception e)
					{
					System.err.println(e);
					}
					

		
	}

	

}
