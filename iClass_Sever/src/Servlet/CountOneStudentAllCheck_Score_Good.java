package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CountOneStudentAllCheck_Score_Good
 */
@WebServlet("/CountOneStudentAllCheck_Score_Good")
public class CountOneStudentAllCheck_Score_Good extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CountOneStudentAllCheck_Score_Good() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String student_id = request.getParameter("student_id");
		String subject_id = request.getParameter("subject_id");
		System.out.println("学生ID：	" + student_id);
		System.out.println("课程ID:	" + subject_id);
		PrintWriter out = response.getWriter();
		String count_sql = "SELECT COUNT(student_id) AS GOOD FROM all_check_info WHERE student_id ='" + student_id
				+ "' and subject_id='" + subject_id + "' and score <> 1 and score <> 2 and score <> 3";
		try {
			// 连接数据库
			java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iclass?useSSL=false",
					"root", "407031");
			Statement statement = conn.createStatement(); // 创建Statement对象
			// 执行SQL语句，获取结果
			ResultSet resultset = statement.executeQuery(count_sql);
			int num_good = 0;
			if (resultset.next()) {
				num_good = resultset.getInt("GOOD");
			}
			// 输出结果
			System.out.println(student_id + " good in the " + subject_id + " is:" + num_good);
			out.println(URLEncoder.encode(String.valueOf(num_good), "UTF-8"));
			// 关闭连接
			resultset.close();
			conn.close();
			statement.close();
		} catch (SQLException se) {
			out.println("count failed");
			System.out.println("SQLException: " + se.getMessage());
		}
	}
}