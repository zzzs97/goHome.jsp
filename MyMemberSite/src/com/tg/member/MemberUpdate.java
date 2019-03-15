package com.tg.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/member/update")
public class MemberUpdate extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "jsp";
		String password = "jsp";

		int mNo = Integer.parseInt(req.getParameter("no"));

		String sql = "";

		try {
			Class.forName(driver);
			System.out.println("오라클 드라이버 로드");

			conn = DriverManager.getConnection(url, user, password);

			sql = "SELECT MNO, EMAIL, MNAME, CRE_DATE";
			sql += " FROM MEMBERS";
			sql += " WHERE MNO = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, mNo);

			rs = pstmt.executeQuery();

			String mName = "";
			String email = "";
			Date creDate = null;

//			ArrayList<MemberDto> memberList = new ArrayList<MemberDto>();
			
			MemberDto memberDto = null;
			if(rs.next()) {
				mNo = rs.getInt("MNO");
				mName = rs.getString("MNAME");
				email = rs.getString("email");
				creDate = rs.getDate("cre_date");
				
				memberDto = new MemberDto();
				
				memberDto.setNo(mNo);
				memberDto.setName(mName);
				memberDto.setEmail(email);
				memberDto.setCreateDate(creDate);
			}
			
//			while (rs.next()) {
//				mNo = rs.getInt("MNO");
//				mName = rs.getString("MNAME");
//				email = rs.getString("email");
//				creDate = rs.getDate("cre_date");
//				
//				memberDto = new MemberDto();
//				
//				memberDto.setNo(mNo);
//				memberDto.setName(mName);
//				memberDto.setEmail(email);
//				memberDto.setCreateDate(creDate);
//				
////				memberList.add(memberDto);
//			}

			req.setAttribute("memberDto", memberDto);
			
			res.setCharacterEncoding("UTF-8");
			RequestDispatcher dispatcher = 
					req.getRequestDispatcher("./memberDetailView.jsp");
			
			dispatcher.include(req, res);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 나중에 예외 처리
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // if(rs != null) end

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} // finally 종료
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement pstmt = null;

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "jsp";
		String password = "jsp";

		req.setCharacterEncoding("UTF-8");

		String email = req.getParameter("email");
		String name = req.getParameter("name");
		int mNo = Integer.parseInt(req.getParameter("no"));

		String sql = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);

			sql = "UPDATE MEMBERS";
			sql += " SET EMAIL = ?, MNAME = ?, MOD_DATE = SYSDATE";
			sql += " WHERE MNO = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, name);
			pstmt.setInt(3, mNo);

			pstmt.executeUpdate();

			res.sendRedirect("./list");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} // finally end

	}

}