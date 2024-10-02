const btn = document.getElementById("login");
btn.addEventListener("click", (evt) => {document.getElementById("modal").style.display = 'block'});
const btnhide = document.getElementById("hide");
btnhide.addEventListener("click", () => document.getElementById("modal").style.display = "none");