package com.keduit;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.crypto.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class _01_Library {
// mysQL에 자료 삽입문
	public static void main(String[] args) throws Exception {
		URL url = new URL(
				"https://api.odcloud.kr/api/3072689/v1/uddi:d678f77d-afc5-498e-8217-de6869c49aa3?page=1&perPage=30&returnType=json&serviceKey=gqMaCDgY0Ze4IZ8mvXROKLJTJdQSJNVf4m2jE10HKAeeaz5kgNGwsz75fDRKXfLnQHvaiPc5CnCuw8yY%2BzL4Uw%3D%3D");

		_03_Library a = new _03_Library();

		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		String result = "";

		BufferedReader bf;

		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		result = bf.readLine();

		// 서명, 저자, 출판년, 출판사
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(result);

		JSONArray data = (JSONArray) jsonObj.get("data");

		for (int i = 0; i < data.size(); i++) {
			JSONObject namE = (JSONObject) data.get(i);
			String nameNm = (String) namE.get("서명");
			String writeR = (String) namE.get("저자");
			String bookyeaR = (String) namE.get("출판년");
			String publisheR = (String) namE.get("출판사");
			int num = i + 1;
			System.out.println("번호 : " + num);
			System.out.println("책이름(서명) : " + nameNm);
			System.out.println("저자 : " + writeR);
			System.out.println("출판년 : " + bookyeaR);
			System.out.println("출판사 : " + publisheR);
			System.out.println();

			_00_Library library = new _00_Library(nameNm, writeR, bookyeaR, publisheR);
			String sql = "insert into library values(?,?,?,?)";
			PreparedStatement pstmt = a.conn.prepareStatement(sql);
			pstmt.setString(1, library.getBookname());
			pstmt.setString(2, library.getBookwriter());
			pstmt.setInt(3, library.getBookyear());
			pstmt.setString(4, library.getPublisher());
			pstmt.executeUpdate();

		}

	}
}
