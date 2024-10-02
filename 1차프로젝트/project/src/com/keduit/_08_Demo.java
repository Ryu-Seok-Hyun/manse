package com.keduit;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class _08_Demo {

	// 하드코딩된 관리자 비밀번호
	private static final String ADMIN_PASSWORD = "my1234"; // 관리자 모드 접근 시 ADMIN_PASSWORD와 사용자 입력을 비교하여 인증을 처리.
	//왜 클래스 안에 넣었냐?
	//ADMIN_PASSWORD 변수가 클래스 레벨에서 정의되면 다른 메서드에서도 접근할 수 있음. 이 변수가 private으로 선언되어 있기 때문에 같은 클래스 내에서만 접근할 수 있으며, 필요한 경우 이 클래스 내의 다른 메서드에서도 사용할 수 있음.
	// ADMIN_PASSWORD는 final 키워드를 사용하여 변경할 수 없는 상수로 정의되어 있고 이는 관리자 비밀번호가 고정된 값임을 나타냄. 클래스 내부에 정의되어 있으면, 이 값이 실수로 변경되지 않고 유지되도록 보장함.
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		_03_Library library; // 변수 library는 _03_Library와 데이터 베이스를 연결해주려고 선언함.

		try { // new _03_Library(); >> 에러. 던지면 편하지만 연결 안될시 에러 메시지 확인하기 위해.
			library = new _03_Library(); // 변수선언한 library에 _03_Library의 객체를 생성해서 library에 넣어줌. 왜? 데이터베이스에 연결 매개체.
		} catch (Exception e) {
			System.err.println("데이터베이스 연결 오류: " + e.getMessage());
			return;
		}
		// 고객이 콘솔창을 열었을때 현재 날자와 시간을 알수 있게끔 구현하고 싶어서 만들었음.
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter); // 포맷 적용하여 문자열로 변환
		System.out.println("*ੈ✩‧₊˚(๑ ᐢ ᵕ ᐢ ๑)𝚈𝙾𝚄𝚁 𝚃𝙷𝙴 𝙱𝙴𝚂𝚃*ੈ✩‧₊˚\n좋은 하루 입니다.\n오늘의 날짜와 시간 알려드릴게요.\n현재 : "
				+ formattedDateTime + " 입니다.");
		System.out.println();

		while (true) {
			System.out.println("----- 선택해주세요✧*｡٩(ˊᗜˋ*)و✧*｡ -----");
			System.out.println("1.책 조회(고객 전용)");
			System.out.println("2.관리자모드(관리자 외 접근금지)");
			System.out.println("3.종료");
			System.out.println("--------------------");
			System.out.println("번호를 입력해주세요 >> ");
			String userInput = scanner.nextLine().trim(); // 사용자 입력 문자열을 읽고 .trim()으로 공백 제거

			if (userInput.equals("1")) {
				BookList(library, scanner); // BookList(library, scanner);는 library 객체를 사용하여 데이터베이스에서 도서를 조회하고,
											//  scanner 객체를 사용하여 사용자의 입력을 받아오는 함수 호출
			} else if (userInput.equals("2")) {
				// 관리자 모드 비밀번호 입력
				System.out.print("비밀번호를 입력하세요: ");
				String password = scanner.nextLine().trim();

				if (password.equals(ADMIN_PASSWORD)) {
					System.out.println("비밀번호 인증 성공. 접속 승인. 관리자 모드로 진입합니다.");

					AdminMode(library, scanner); // 관리자 모드 메서드 호출해서 관리자 while문으로 이동
				} else {
					System.out.println("접속 거부. 비밀번호가 올바르지 않습니다. 다시 시도해주세요.");
				}
			} else if (userInput.equals("3")) {
				System.out.println("프로그램을 종료합니다.");
				try {
					library.closeConnection(); // closeConnection() 메서드 호출 시 SQLException 처리
				} catch (SQLException e) {
					System.err.println("데이터베이스 연결 해제 오류 : " + e.getMessage());
				} finally {
					scanner.close(); // 스캐너도 닫아주는 것이 좋을것 같음.
				}
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
			}
		}
	}

	// 고객이 보는 메뉴
	private static void BookList(_03_Library library, Scanner scanner) {
        try {
            while (true) {
                System.out.println("---- 책 조회 메뉴 ----");
                System.out.println("1.모든 도서 조회");
                System.out.println("2.도서명으로 검색");
                System.out.println("3.저자명으로 검색");
                System.out.println("4.출판연도로 검색");
                System.out.println("5.출판사로 검색");
                System.out.println("6.뒤로 가기");
                System.out.print("번호를 입력해주세요 >> ");

                String userInput = scanner.nextLine().trim();

                if (userInput.equals("1")) {
                    library.selectList();
                } else if (userInput.equals("2")) {
                    System.out.print("검색할 도서명을 입력하세요 : ");
                    String titleKeyword = scanner.nextLine().trim();
                    library.searchBooksByTitle(titleKeyword);
                } else if (userInput.equals("3")) {
                    System.out.print("검색할 저자명을 입력하세요 : ");
                    String authorKeyword = scanner.nextLine().trim();
                    library.searchBooksByAuthor(authorKeyword);
                } else if (userInput.equals("4")) {
                    System.out.print("검색할 출판연도를 입력하세요 : ");
                    try {
                        int searchYear = Integer.parseInt(scanner.nextLine().trim());
                        library.searchBooksByYear(searchYear);
                    } catch (NumberFormatException e) {
                        System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                    }
                } else if (userInput.equals("5")) {
                    System.out.print("검색할 출판사명을 입력하세요 : ");
                    String publisherKeyword = scanner.nextLine().trim();
                    library.searchBooksByPublisher(publisherKeyword);
                } else if (userInput.equals("6")) {
                    break;
                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            }
        } catch (SQLException e) {
            System.out.println("데이터베이스 조회 중 오류 발생 : " + e.getMessage());
        }
    }

	// 관리자 모드 메뉴 처리
	private static void AdminMode(_03_Library library, Scanner scanner) {
		try {
			while (true) {
				System.out.println("---- 관리자 모드 ----");
				System.out.println("1.책 조회");
				System.out.println("2.도서 추가");
				System.out.println("3.도서 수정 및 업데이트");
				System.out.println("4.도서 삭제");
				System.out.println("5.뒤로 가기");
				System.out.print("번호를 입력해주세요 >> ");
				String userInput = scanner.nextLine().trim();

				if (userInput.equals("1")) {
					BookList(library, scanner); // 책 조회 메서드 호출
				//	library.selectList();
					
				} else if (userInput.equals("2")) {
					System.out.print("도서명을 입력하세요 : ");
					String bookname = scanner.nextLine().trim();
					String regexPattern = "^" + bookname.substring(0, Math.min(bookname.length(), 3));

					System.out.print("저자명을 입력하세요 : ");
					String bookwriter = scanner.nextLine().trim();
					String regexPattern1 = "^" + bookwriter.substring(0, Math.min(bookwriter.length(), 3));
				    Pattern pattern1 = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

					System.out.print("출판년도를 입력하세요 : ");
					int bookyear = Integer.parseInt(scanner.nextLine().trim());
					

					System.out.print("출판사를 입력하세요 : ");
					String publisher = scanner.nextLine().trim();
					String regexPattern2 = "^" + publisher.substring(0, Math.min(publisher.length(), 3)); //출판사 이름(publisher)을 기반으로 정규 표현식 패턴(pattern2)을 생성하는 부분
					Pattern pattern2 = Pattern.compile(regexPattern2, Pattern.CASE_INSENSITIVE); 			// publisher.substring(0, Math.min(publisher.length(), 3)): publisher 문자열의 처음부터 세 글자까지의 부분 문자열을 가져옴. 이것은 출판사 이름의 첫 세 글자를 포함하는 문자열임.
																											// "^" + ...: 정규 표현식에서 ^는 입력 문자열의 시작을 의미해. 따라서 이 부분은 출판사 이름이 정확히 이 세 글자로 시작하는 경우에 매치한다.
					library.insertLibrary(bookname, bookwriter, bookyear, publisher);						//Pattern.compile(regexPattern2, Pattern.CASE_INSENSITIVE): regexPattern2 문자열을 정규 표현식 패턴으로 컴파일함.
					System.out.println("도서 추가가 완료되었습니다.");												// Pattern.CASE_INSENSITIVE 옵션은 패턴 매칭 시 대소문자를 구분하지 않도록 설정. 즉, 검색할 때 대소문자를 무시하고 매치를 수행합
				} 
				else if (userInput.equals("3")) {
					System.out.print("수정 및 업데이트 할 도서의 이름을 입력하세요 : ");
					String bookname = scanner.nextLine().trim();

					System.out.print("새로운 저자를 입력하세요 : ");
					String newWriter = scanner.nextLine().trim();

					System.out.print("새로운 출판연도를 입력하세요 : ");
					int newYear = Integer.parseInt(scanner.nextLine().trim());

					System.out.print("새로운 출판사를 입력하세요 : ");
					String newPublisher = scanner.nextLine().trim();

					try {
						library.update(bookname, newWriter, newYear, newPublisher);
						System.out.println("도서의 수정 및 업데이트가 완료되었습니다.");
					} catch (SQLException e) {
						System.out.println("도서의 수정 및 업데이트 도중 오류가 발생했습니다: " + e.getMessage());
					}

				} else if (userInput.equals("4")) {
					System.out.print("삭제할 도서의 이름을 입력하세요 : ");
					String bookname = scanner.nextLine().trim();

					library.delete(bookname);
					System.out.println("도서 삭제가 완료되었습니다.");

				}
				// 뒤로가기
				else if (userInput.equals("5")) {
					break;
				} else {
					System.out.println("잘못된 입력입니다. 다시 선택해주세요.");

				}
			}
		} catch (SQLException e) {
			System.out.println("데이터베이스 처리 중 오류 발생 : " + e.getMessage());
		}
	}
}
//catch문을 사용하는 이유는 프로그램이 데이터베이스와 상호작용할 때 발생할 수 있는 예외를 처리하기 위함
//따라서 catch문을 사용하여 예외를 적절히 처리하고 오류 메시지를 출력함으로써 사용자에게 친숙하고 이해하기 쉬운 방식으로 오류 상황을 알릴 수 있음. 이렇게 하면 사용자가 프로그램에서 발생한 문제를 이해하고, 필요한 조치를 취할 수 있게 됨.