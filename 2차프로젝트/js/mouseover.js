function showInfoBox(event) {
  const infoBox = document.getElementById('info-box');
  infoBox.textContent = event.target.getAttribute('id');
  infoBox.style.display = 'block';
  infoBox.style.left = `${event.pageX + 10}px`;
  infoBox.style.top = `${event.pageY + 10}px`;
}

function hideInfoBox() {
  const infoBox = document.getElementById('info-box');
  infoBox.style.display = 'none';
}

// 모든 path 요소에 마우스 오버 이벤트 추가
document.querySelectorAll('#map path').forEach(path => {
  path.addEventListener('mouseenter', showInfoBox);
  path.addEventListener('mouseleave', hideInfoBox);
});