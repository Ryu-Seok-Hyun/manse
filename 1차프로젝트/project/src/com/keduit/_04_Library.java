package com.keduit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class _04_Library {
// select 문
	public static void main(String[] args) throws Exception {
		_03_Library library = new _03_Library();

		// 도서 목록 조회
		library.selectList();
	}
}