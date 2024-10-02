// 체크박스에 체크를 적용했는데도 불구하고
// 아래 페이지 번호수가 변하지 않은 부분 코드 수정.
// 이제 체크박스를 적용하여도 아래 페이지수가 페이지에 맞게 수정함.
// 다른 기능은 동일.




document.addEventListener('DOMContentLoaded', () => {
    const galleryItems = Array.from(document.querySelectorAll('.gallery-item')); //array 추가
    const imagesPerPage = 20;
    let currentPage = 1;
    let totalPages = Math.ceil(galleryItems.length / imagesPerPage); //갤러리 아이템과 페이지맞게
    let filteredItems = galleryItems.slice(); // 전체 항목을 복사하여 필터링된 항목을 별도로 관리

    function updatePaginationInfo() { 
        document.getElementById('currentPage').textContent = currentPage;
        document.getElementById('totalPages').textContent = totalPages;
    }

    function updateDisplay() { 
        const start = (currentPage - 1) * imagesPerPage;
        const end = start + imagesPerPage;

        filteredItems.forEach((item, index) => {
            if (index >= start && index < end) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    }

    function filterItems() {
        const checkboxes = document.querySelectorAll('.filter-box input[type="checkbox"]');
        const checkedLocations = Array.from(checkboxes)
            .filter(input => input.checked)
            .map(input => input.value);

        filteredItems = galleryItems.filter(item => {
            const itemLocation = item.getAttribute('data-location');
            if (checkedLocations.length === 0 || checkedLocations.includes(itemLocation)) {
                item.style.display = 'block'; // 필터 적용 후 보이도록 설정
                return true;
            } else {
                item.style.display = 'none';
                return false;
            }
        });

        totalPages = Math.ceil(filteredItems.length / imagesPerPage);
        currentPage = 1; // 필터 변경 시 첫 페이지로 돌아가기
        updateDisplay();
        updatePaginationInfo();
    }

    function nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        updateDisplay();
        updatePaginationInfo();
        window.scrollTo({ top: 0});
        savePageState(); // 페이지 상태 저장
    }

    function prevPage() {
        if (currentPage > 1) {
            currentPage--;
        } else {
            currentPage = totalPages;
        }
        updateDisplay();
        updatePaginationInfo();
        window.scrollTo({ top: 0});
        savePageState(); // 페이지 상태 저장
    }

    function savePageState() {
        pageHistory.push({
            page: currentPage,
            filters: Array.from(document.querySelectorAll('.filter-box input[type="checkbox"]'))
                .map(cb => ({ value: cb.value, checked: cb.checked }))
        });
    }

    function loadPageState() {
        if (pageHistory.length > 0) {
            const lastState = pageHistory.pop();
            currentPage = lastState.page;
            lastState.filters.forEach(filter => {
                const checkbox = document.querySelector(`.filter-box input[type="checkbox"][value="${filter.value}"]`);
                if (checkbox) checkbox.checked = filter.checked;
            });
            filterItems();
            updateDisplay();
            updatePaginationInfo();
        }
    }

    document.getElementById('nextButton').addEventListener('click', nextPage);
    document.getElementById('prevButton').addEventListener('click', prevPage);
    document.getElementById('scrollToTop').addEventListener('click', () => {
        window.scrollTo({ top: 0});
    });

    document.getElementById('scrollToBottom').addEventListener('click', () => {
        window.scrollTo({ top: document.body.scrollHeight});
    });

    document.querySelectorAll('.filter-box input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            filterItems();
            savePageState(); // 필터 상태 변경 시 페이지 상태 저장
        });
    });

    filterItems(); // 초기 필터링 적용. 
    updatePaginationInfo();
    updateDisplay();
});
