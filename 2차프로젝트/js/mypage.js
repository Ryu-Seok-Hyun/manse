// 기본 테두리 색상
const defaultBorderColor = 'none';
// 활성 상태에서의 테두리 색상
const activeBorderColor = 'black';
// 비활성 상태에서의 테두리 색상
const disabledBorderColor = 'rgb(245, 240, 221)';

// 전화번호 변경 함수
function change_phone() {
    const phoneField = document.getElementById("phone");
    phoneField.disabled = false;
    phoneField.style.border = `1px solid ${activeBorderColor}`; // 테두리 색상 변경
    const phoneButton = document.getElementById("phone_button");
    phoneButton.value = "확정";
    phoneButton.style.color = "gold";
    phoneButton.setAttribute("onclick", "decide_phone()");
}

// 전화번호 함수
function decide_phone() {
    const phoneField = document.getElementById("phone");
    if (!/^\d{10,15}$/.test(phoneField.value)) {
        alert("전화번호는 10자에서 15자 사이의 숫자만 입력할 수 있습니다.");
        return;
    }
    document.getElementById("submit").disabled = false;
    document.getElementById("phone2").value = phoneField.value;
    phoneField.disabled = true;
    phoneField.style.border = `1px solid ${disabledBorderColor}`; // 비활성 상태에서의 테두리 색상
    document.getElementById("phone_button").disabled = true;
    document.getElementById("phone_button").value = "변경 되었습니다.";
    document.getElementById("phone_button").style.color = "rgb(42, 66, 38)";
    document.getElementById("phone_button").style.backgroundColor = "rgb(245,240,221)";
    alert("전화번호가 변경되었습니다.");
}

// 이메일 변경 함수
function change_email() {
    const emailField = document.getElementById("email");
    emailField.disabled = false;
    emailField.style.border = `1px solid ${activeBorderColor}`; // 테두리 색상 변경
    const emailButton = document.getElementById("email_button");
    emailButton.value = "확정";
    emailButton.style.color = "gold";
    emailButton.setAttribute("onclick", "decide_email()");
}

// 이메일 확정 함수
function decide_email() {
    const emailField = document.getElementById("email");
    if (!/\S+@\S+\.\S+/.test(emailField.value)) {
        alert("유효한 이메일 주소를 입력해 주세요.");
        return;
    }
    document.getElementById("submit").disabled = false;
    document.getElementById("email2").value = emailField.value;
    emailField.disabled = true;
    emailField.style.border = `1px solid ${disabledBorderColor}`; // 비활성 상태에서의 테두리 색상
    document.getElementById("email_button").disabled = true;
    document.getElementById("email_button").value = "변경 되었습니다.";
    document.getElementById("email_button").style.color = "rgb(42, 66, 38)";
    document.getElementById("email_button").style.backgroundColor = "rgb(245,240,221)";
    alert("이메일이 변경되었습니다.");
}

// 비밀번호 변경 함수
function change_pw() {
    document.getElementById("pw").disabled = false;
    document.getElementById("pw").style.border = `1px solid ${activeBorderColor}`; // 테두리 색상 변경
    const pwButton = document.getElementById("pw_button");
    pwButton.value = "확정";
    pwButton.style.color = "hotpink";
    pwButton.setAttribute("onclick", "decide_pw()");
}

// 비밀번호 확정 함수
function decide_pw() {
    const pwField = document.getElementById("pw");
    const pwButton = document.getElementById("pw_button");
    if (pwField.value.length < 6) {
        alert("비밀번호는 최소 6자 이상이어야 합니다.");
        return;
    }
    document.getElementById("submit").disabled = false;
    document.getElementById("pw2").value = pwField.value;
    pwField.disabled = true;
    pwField.style.border = `1px solid ${disabledBorderColor}`; // 비활성 상태에서의 테두리 색상
    pwButton.disabled = true;
    pwButton.value = "확정됨";
    pwButton.style.color = "#ccc";
    pwButton.style.backgroundColor = "#ddd";
}


function showPasswordModal() {
    document.getElementById("passwordModal").style.display = "block";
}


function closePasswordModal() {
    document.getElementById("passwordModal").style.display = "none";
}


function submitPasswordChange() {
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    if (newPassword.length < 6) {
        alert("새 비밀번호는 최소 6자 이상이어야 합니다.");
        return;
    }
    if (newPassword !== confirmPassword) {
        alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        return;
    }
    document.getElementById("pw").value = newPassword;
    document.getElementById("pw2").value = newPassword;
    document.getElementById("pw").disabled = true;
    document.getElementById("pw").style.border = `1px solid ${disabledBorderColor}`; // 비활성 상태에서의 테두리 색상
    const pwButton = document.getElementById("pw_button");
    pwButton.value = "변경 되었습니다.";
    pwButton.style.color = "rgb(42, 66, 38)";
    pwButton.style.backgroundColor = "rgb(245,240,221)";
    pwButton.disabled = true;
    document.getElementById("submit").disabled = false;
    alert("비밀번호가 변경되었습니다.");
    closePasswordModal();
}


document.getElementById('myForm').onsubmit = function (event) {
    event.preventDefault();

  
    var email = document.getElementById("email2").value;
    var phone = document.getElementById("phone2").value;
    var pw = document.getElementById("pw2").value;

    if (!/\S+@\S+\.\S+/.test(email)) {
        alert("유효한 이메일 주소를 입력해 주세요.");
        return;
    }

    if (!/^\d{10,15}$/.test(phone)) {
        alert("전화번호는 10자에서 15자 사이의 숫자만 입력할 수 있습니다.");
        return;
    }

    if (pw.length < 6) {
        alert("비밀번호는 최소 6자 이상이어야 합니다.");
        return;
    }

    alert('개인정보 변경이 완료 되었습니다.\n메인 홈페이지로 이동합니다.');
    setTimeout(function () {
        window.location.href = 'http://127.0.0.1:5500/main.html';
    }, 1000);
};
