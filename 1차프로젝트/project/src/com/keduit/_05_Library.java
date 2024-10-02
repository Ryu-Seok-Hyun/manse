package com.keduit;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class _05_Library {
// delete 문
	public static void main(String[] args) throws Exception {
		_03_Library library = new _03_Library();
		// 도서 삭제 
		String bookname = "홍길동전"; // 삭제할 도서의 제목 지정
		library.delete(bookname);
		
	}
}