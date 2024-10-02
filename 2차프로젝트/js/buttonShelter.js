// 전체 보호소 데이터 (예시)
const allData = [
    { name: '서울 보호소', num: '02-123-4567', addr: '서울특별시' },
    { name: '부산 보호소', num: '051-987-6543', addr: '부산광역시' },
    { name: '대구 보호소', num: '053-123-7890', addr: '대구광역시' },
    { name: '인천 보호소', num: '032-456-1234', addr: '인천광역시' },
    { name: '대전 보호소', num: '042-789-4561', addr: '대전광역시' },
    { name: '울산 보호소', num: '052-223-6274', addr: '울산광역시' },
    { name: '광주 보호소', num: '062-456-7890', addr: '광주광역시' },
    { name: '세종 보호소', num: '044-789-1234', addr: '세종특별자치시' },
    { name: '경기 보호소', num: '031-123-4567', addr: '경기도' },
    { name: '경남 보호소', num: '055-987-6543', addr: '경상남도' },
    { name: '경북 보호소', num: '054-123-7890', addr: '경상북도' },
    { name: '전남 보호소', num: '061-456-1234', addr: '전라남도' },
    { name: '전북 보호소', num: '063-789-4561', addr: '전라북도' },
    { name: '충남 보호소', num: '041-456-7890', addr: '충청남도' },
    { name: '충북 보호소', num: '043-123-4567', addr: '충청북도' },
    { name: '강원 보호소', num: '033-987-6543', addr: '강원도' },
    { name: '제주 보호소', num: '064-123-7890', addr: '제주특별자치도' }
];

const itemsPerPage = 13; //한 페이지에 표시할 보호소 수
let currentPage = 1;     //현재 페이지 번호
let filteredData = [];   //필터링된 데이터

function displayItems(pageNumber) {
    const tbody = document.getElementById('shelterInfoBody');    
    tbody.innerHTML = ''; // 기존의 내용 삭제
    
    // 현재 페이지에 해당하는 항목의 시작과 끝 인덱스 계산
    const startIndex = (pageNumber - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, filteredData.length);
    const itemsToDisplay = filteredData.slice(startIndex, endIndex);
    
    // 필터링된 항목을 테이블에 추가
    itemsToDisplay.forEach(item => {
        const newRow = document.createElement('tr');

        const nameCell = document.createElement('td');
        nameCell.textContent = item.name;
        const numCell = document.createElement('td');
        numCell.textContent = item.num;

        const addrCell = document.createElement('td');
        addrCell.textContent = item.addr;
        addrCell.classList.add('address'); // 주소 셀에 스타일 클래스 추가

        newRow.appendChild(nameCell);
        newRow.appendChild(numCell);
        newRow.appendChild(addrCell);

        tbody.appendChild(newRow);
    });

    updatePagination();
}
// 페이지네이션 버튼을 업데이트
function updatePagination() {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = ''; // 기존의 페이지네이션 내용 삭제

    // 전체 페이지 수 계산
    const totalPages = Math.ceil(filteredData.length / itemsPerPage);

    // 페이지 번호 버튼 생성
    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.classList.add('page-btn');
        if (i === currentPage) {
            button.classList.add('selected'); // 현재 페이지 버튼 강조
        }
        button.addEventListener('click', () => changePage(i)); // 버튼 클릭 시 페이지 변경
        pagination.appendChild(button);
    }
}

// 페이지 번호를 변경
function changePage(pageNumber) {
    currentPage = pageNumber;
    displayItems(pageNumber); // 선택한 페이지의 항목을 표시
}

// 지역 탭 클릭
function mapTabClick(element) {
    // 모든 li 요소에서 active 클래스 제거
    const allTabs = document.querySelectorAll('.map-tabs li');
    allTabs.forEach(tab => {
        tab.classList.remove('active');

    });

    // 클릭된 요소에 active 클래스 추가
    element.classList.add('active');

    // 데이터 속성에서 보호소 정보 가져오기
    const id = element.getAttribute('id');
    const addr = element.getAttribute('data-addr');


    // 전체 데이터나 필터링된 데이터 설정
    if (id === '전체') {
        filteredData = allData; // 전체 데이터 표시
    } else {
        filteredData = allData.filter(item => item.addr.includes(addr)); // 지역별 데이터 필터링
    }

    currentPage = 1; // 페이지를 1로 초기화
    displayItems(currentPage); // 데이터 표시
}

document.addEventListener('DOMContentLoaded', () => {
    displayItems(currentPage); // 페이지 로드 시 초기 데이터 표시
});