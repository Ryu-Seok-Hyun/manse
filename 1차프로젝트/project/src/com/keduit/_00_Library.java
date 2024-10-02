package com.keduit;

import org.json.simple.JSONObject;

public class _00_Library {
	private String bookname; // 서명
	private String bookwriter; // 저자
	private int bookyear;  // 출판년
	private String publisher; // 출판사 

	
// 매개변수로 갖는 생성자
	public _00_Library(String namE, String bookwriter, String bookyeaR2, String publisher) {
		this.bookname = namE;
		this.bookwriter = bookwriter;
		this.bookyear = Integer.valueOf(bookyeaR2);
		this.publisher = publisher;
	}

// 각 필드의 get set
	public String getBookname() {
		return bookname;
	}


	public void setBookname(String bookname) {
		this.bookname = bookname;
	}


	public String getBookwriter() {
		return bookwriter;
	}


	public void setBookwriter(String bookwriter) {
		this.bookwriter = bookwriter;
	}


	public int getBookyear() {
		return bookyear;
	}


	public void setBookyear(int bookyear) {
		this.bookyear = bookyear;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	
	
//toString 오버라이드
	@Override
	public String toString() {
		return "_00_Library [bookname=" + bookname + ", bookwriter=" + bookwriter + ", bookyear=" + bookyear
				+ ", publisher=" + publisher + "]";
	}
	
	
}
