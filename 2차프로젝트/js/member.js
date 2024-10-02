// member.js
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const password = document.getElementById('password').value;
    const messageElement = document.getElementById('message');
    
    // 인증 비밀번호 (실제 환경에서는 서버와 통신하여 검증 해야되는)
    const validPassword = 'aaa'; // 여기에 실제 비밀번호를 임시설정.
    
    if (password === validPassword) {
        messageElement.textContent = '마이페이지로 이동합니다.';
        messageElement.style.color = 'green';
        
        // 비밀번호가 맞다면 마이페이지로 이동
        setTimeout(function() {
            window.location.href = 'http://127.0.0.1:5501/mypage.html'; // 마이페이지의 URL
        }, 300);
    } else {
        messageElement.textContent = '비밀번호가 틀렸습니다.';
        messageElement.style.color = 'red';
    }
});

// 뒤로가기 버튼 이벤트 리스너 추가
document.getElementById('backButton').addEventListener('click', function() {
    window.location.href = 'http://127.0.0.1:5501/main.html'; // 메인 홈페이지의 URL
});
