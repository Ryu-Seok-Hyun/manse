package com.keduit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class _03_Library {

	Connection conn;

	public _03_Library() throws Exception {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/market_db";
		String userid = "root";
		String pwd = "my1234";

		Class.forName(driver);
		System.out.println("드라이버 연결 췍! 성공! 좋았어 가자구~!");
		System.out.println("데이터베이스 연결 준비중(제발..)");
		conn = DriverManager.getConnection(url, userid, pwd);
		System.out.println("데이터베이스 연결 췍! 성공!\n오늘 하루도 화이팅! *.☆⸜(⑉˙ᗜ˙⑉)⸝♡.*");
		System.out.println("(* 기기 오픈시 관리자만 보이는 연결멘트 *)");
		System.out.println();
	}

	// 첫번째 insert 하기 >> 완료
	public void insertlibrary(_00_Library library) throws SQLException {
		String sql = "insert into library(bookname, bookwriter, bookyear, publisher)" + " values(?,?,?,?)"; // 와일드카드 사용
																											// 잘해야됨.★★★★★★
		// sql문이 매개변수의 값으로 배치되는 경우 PrepareStatment객체를 사용.
		PreparedStatement pstmt = conn.prepareStatement(sql); // 동적쿼리를 쓸때에는 PreparedStatement 써야됨. // 매개변수가 변동이 많거나 변수를
																// 모를때 동적쿼리

		// sql문의 ?(=와일드카드)의 요소에 순번에 맞게 값을 넣어줌.
		pstmt.setString(1, library.getBookname());
		pstmt.setString(2, library.getBookwriter());
		pstmt.setInt(3, library.getBookyear());
		pstmt.setString(4, library.getPublisher());

		int result = pstmt.executeUpdate();
		if (result == 1) {
			System.out.println(library.getBookname() + "레코드 추가 성공!");
		} else {
			System.out.println(library.getBookname() + "레코드 추가 실패!");
		}
		pstmt.close();
	}

	
	public void selectList() throws SQLException {
		String sql = "SELECT * FROM library";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		int totalBooks = 0; 

		int rowNum = 1; 

		System.out.printf("%-4s %-60s %-40s %-30s %-60s\n", "번호", "도서명", "저자", "출판년도", "출판사");
		System.out.println(
				"===========================================================================================================================================================================");

		while (rs.next()) {
			String bookname = rs.getString("bookname");
			String bookwriter = rs.getString("bookwriter");
			int bookyear = rs.getInt("bookyear");
			String publisher = rs.getString("publisher");

			System.out.printf("%-4d %-60s %-40s %-30d %-60s\n", rowNum, bookname, bookwriter, bookyear, publisher);
			rowNum++; 
			totalBooks++; 
		}
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.println("총 " + totalBooks + "권의 도서가 조회되었습니다.");
	

		rs.close();
		stmt.close();
	}

	// 세번째 delete하기 - 완
	public void delete(String bookname) throws SQLException {
		String sql = "delete from library where bookname = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, bookname);

		int result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 도서가 성공적으로 삭제되었습니다.");
			
		} else {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 삭제 실패! 해당 도서가 존재하지 않습니다.");
		}
		

		pstmt.close();
	}

	// 네번쨰 insert 하기 >> 완
	public void insertLibrary(String bookname, String bookwriter, int bookyear, String publisher) throws SQLException {
		String sql = "INSERT INTO library (bookname, bookwriter, bookyear, publisher) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, bookname);
		pstmt.setString(2, bookwriter);
		pstmt.setInt(3, bookyear);
		pstmt.setString(4, publisher);

		int result = pstmt.executeUpdate();

		if (result == 1) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 도서 추가 성공!");
			
		} else {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 도서 추가 실패!");
			
		}

		pstmt.close();
	}

	
	public void update(String bookname, String newWriter, int newYear, String newPublisher) throws SQLException {
		String sql = "UPDATE library SET bookwriter = ?, bookyear = ?, publisher = ? WHERE bookname = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, newWriter);
		pstmt.setInt(2, newYear);
		pstmt.setString(3, newPublisher);
		pstmt.setString(4, bookname);

		int result = pstmt.executeUpdate();

		if (result == 1) {
			 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 도서 정보 수정 및 업데이트 성공! ");
		
		} else {
			 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println(bookname + " 도서 정보 수정 및 업데이트 실패! 해당 도서가 존재하지 않거나 수정에 실패했습니다. ");
			
		}

		pstmt.close();
	}

	// 닫는 메서드 추가
	public void closeConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
			System.out.println("데이터베이스 연결을 종료합니다. 안녕히 가십시오.");
		}
	}

// 검색 메소드
	public void searchBooksByAuthor(String author) throws SQLException {
		String sql = "SELECT * FROM library WHERE bookwriter = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, author);
			ResultSet rs = pstmt.executeQuery();

			List<String> results = new ArrayList<>();
			while (rs.next()) {
				String bookname = rs.getString("bookname");
				String bookwriter = rs.getString("bookwriter");
				int bookyear = rs.getInt("bookyear");
				String publisher = rs.getString("publisher");

				String result = String.format("%-60s %-40s %-30d %-60s", bookname, bookwriter, bookyear, publisher);
				results.add(result);
			}

			if (!results.isEmpty()) {
				 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과 : ");
				 
				for (String result : results) {
					System.out.println(result);
					System.out.println();
				}
			} else { System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과가 없습니다. 처음으로 돌아갑니다.");
				
			}

		}
	}

// 도서명 검색
	public void searchBooksByTitle(String title) throws SQLException {
		String sql = "SELECT * FROM library WHERE bookname LIKE ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + title + "%");
			ResultSet rs = pstmt.executeQuery();

			List<String> results = new ArrayList<>();
			while (rs.next()) {
				String bookname = rs.getString("bookname");
				String bookwriter = rs.getString("bookwriter");
				int bookyear = rs.getInt("bookyear");
				String publisher = rs.getString("publisher");

				String result = String.format("%-60s %-40s %-30d %-60s", bookname, bookwriter, bookyear, publisher);
				results.add(result);
			}

			if (!results.isEmpty()) {
				 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과:");
				
				for (String result : results) {
					System.out.println(result);

				}
			} else { System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과가 없습니다. 처음으로 돌아갑니다.");
				
			}
		}
	}

	// 연도 검색
	public void searchBooksByYear(int year) throws SQLException {
		String sql = "SELECT * FROM library WHERE bookyear = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, year);
			ResultSet rs = pstmt.executeQuery();

			List<String> results = new ArrayList<>();
			while (rs.next()) {
				String bookname = rs.getString("bookname");
				String bookwriter = rs.getString("bookwriter");
				int bookyear = rs.getInt("bookyear");
				String publisher = rs.getString("publisher");

				String result = String.format("%-60s %-40s %-30d %-60s", bookname, bookwriter, bookyear, publisher);
				results.add(result);
			}

			if (!results.isEmpty()) {
				 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과 : ");
				
				for (String result : results) {
					System.out.println(result);
				}
			} else { System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과가 없습니다.");
				 
			}
		}
	}

	// 출판사 검색
	public void searchBooksByPublisher(String publisher) throws SQLException {
		String sql = "SELECT * FROM library WHERE publisher LIKE ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + publisher + "%");
			ResultSet rs = pstmt.executeQuery();

			List<String> results = new ArrayList<>();
			while (rs.next()) {
				String bookname = rs.getString("bookname");
				String bookwriter = rs.getString("bookwriter");
				int bookyear = rs.getInt("bookyear");
				String resultPublisher = rs.getString("publisher");

				String result = String.format("%-60s %-40s %-30d %-60s", bookname, bookwriter, bookyear,
						resultPublisher);
				results.add(result);
			}

			if (!results.isEmpty()) {
				 System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과 : ");
				 
				for (String result : results) {
					System.out.println(result);
				}
			} else { System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("검색 결과가 없습니다.");
				
			}
		}
	}
}
