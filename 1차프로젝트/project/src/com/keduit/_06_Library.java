package com.keduit;

public class _06_Library {
// insert 추가 
	 public static void main(String[] args) throws Exception {
	        _03_Library library = new _03_Library();

	        // 도서 추가 
	        String bookname = "홍길동전";
	        String bookwriter = "허균";
	        int bookyear = 2000;
	        String publisher = "출판사A";

	        library.insertLibrary(bookname, bookwriter, bookyear, publisher);
	    }
	}
