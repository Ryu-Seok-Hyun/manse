// 모달창 요소와 링크, 닫기 버튼 요소
var modal = document.getElementById("myModal");
var trigger = document.getElementById("modalTrigger");
var closeBtn = document.getElementsByClassName("close")[0];

// 문구 클릭 시 모달창을 띄우기
trigger.addEventListener('click', function() {
    modal.style.display = "block";
});

// 닫기 버튼 클릭 시 모달창 닫기
closeBtn.addEventListener('click', function() {
    modal.style.display = "none";
});

// 모달창 외부 클릭 시 모달창 닫기
window.addEventListener('click', function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
});

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ메인ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

document.addEventListener('DOMContentLoaded', () => {
    const gallery = document.querySelector('.gallery');
    const galleryItems = document.querySelectorAll('.gallery-item');
    const imagesPerView = 20; // 한 번에 보여지는 이미지 수
    const totalImages = galleryItems.length;
    let currentIndex = 0; // 현재 페이지 인덱스
    let currentPage = 1; // 현재 페이지
    const imagesPerPage = 20; // 한 페이지당 이미지 수
    let totalPages = Math.ceil(totalImages / imagesPerPage); // 전체 페이지 수
    
    // 페이지 정보를 업데이트하는 함수
    function updatePaginationInfo() {
        document.getElementById('currentPage').textContent = currentPage;
        document.getElementById('totalPages').textContent = totalPages;
    }

    // 초기 상태 설정
    updateDisplay();
    updatePaginationInfo();

    // 다음 페이지로 넘어가는 함수
    function nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updateDisplay();
            updatePaginationInfo();
            window.scroll(0, 0);
        }
    }
    
    function prevPage() {
        if (currentPage > 1) {
            currentPage--;
            updateDisplay();
            updatePaginationInfo();
            window.scroll(0, 0);
        }
    }

    // 다음 버튼 클릭 이벤트
    const nextButton = document.getElementById('nextButton');
    nextButton.addEventListener('click', () => {
        nextPage();
    });

    // 이전 버튼 클릭 이벤트
    const prevButton = document.getElementById('prevButton');
    prevButton.addEventListener('click', () => {
        prevPage();
    });

    // 이미지를 업데이트하는 함수
    function updateDisplay() {
        const start = (currentPage - 1) * imagesPerPage;
        const end = start + imagesPerPage;
        galleryItems.forEach((item, index) => {
            if (index >= start && index < end) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    }
});


document.addEventListener('DOMContentLoaded', () => {
    // 상단으로 스크롤 : behavior: 'smooth'  스무스 효과 
    document.getElementById('scrollToTop').addEventListener('click', () => {
        window.scrollTo({ top: 0});
    });

    // 하단으로 스크롤
    document.getElementById('scrollToBottom').addEventListener('click', () => {
        window.scrollTo({ top: document.body.scrollHeight});
    });

    // 위 아래 스크롤
    document.getElementById('nextButton').addEventListener('click', nextPage);
    document.getElementById('prevButton').addEventListener('click', prevPage);

    updateDisplay();
    updatePaginationInfo();
});
// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
