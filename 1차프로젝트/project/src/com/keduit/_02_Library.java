package com.keduit;

public class _02_Library {
// 데이터 수정
	public static void main(String[] args) throws Exception {
		_03_Library library = new _03_Library();

		// 도서 업데이트
		String bookname = "홍길동전"; // 업데이트할 도서 제목을 지정
		int bookyear = 2023; // 수정할 출판년도
		String publisher = "한국출판사"; // 수정할 출판사
		String bookwriter = "석현2";

		 library.update(bookname, publisher, bookyear, bookwriter);
	}
}
