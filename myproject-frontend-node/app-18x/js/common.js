const __restServer = "http://localhost:8888";
const __csrfToken = getCsrfToken();

console.log(``)

document.addEventListener("DOMContentLoaded", () => {
  // DOM Tree 를 완성한 후, 렌더링 전에 호출됨
  loadHeader();
  getUserInfo();

  loadFooter();
});

function loadHeader() { // 페이지 헤더 로딩
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "/header.html", false);
  xhr.send();

  let parser = new DOMParser();
  let doc = parser.parseFromString(xhr.responseText, "text/html");

  document.body.insertBefore(doc.querySelector("#page-header"), document.body.firstChild);

}

function loadFooter() { // 페이지 푸터 로딩
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "/footer.html", false);
  xhr.send();

  let parser = new DOMParser();
  let doc = parser.parseFromString(xhr.responseText, "text/html");

  document.body.appendChild(doc.querySelector("#page-footer"));
}

function getUserInfo() { // 로그인 사용자 정보 로딩
  let xhr = new XMLHttpRequest();
  xhr.open("GET", `${__restServer}/auth/user-info`, false);
  xhr.withCredentials = true;
  xhr.send();

  let result = JSON.parse(xhr.responseText);
  if (result.status == "success") {
    document.querySelector(".logged-out").style["display"] = "none";
    document.querySelector("#user-name").innerHTML = result.data.name;
  } else {
    document.querySelector(".logged-in").style["display"] = "none";
  }
}

function logout() {
  let xhr = new XMLHttpRequest();
  xhr.open("POST", `${__restServer}/logout`, false);
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.setRequestHeader("X-XSRF-TOKEN", __csrfToken); // 요청 헤더로 전송할 때
  xhr.withCredentials = true;
  xhr.send();
  location.href = "/home.html";
}

function getCsrfToken() {
  // const cookie = document.cookie.split("; ").find(row => row.startsWith("XSRF-TOKEN="));
  // return cookie ? decodeURIComponent(cookie.split("=")[1]) : null;
  return getCookie("XSRF-TOKEN");
}

function getCookie(name) {
  const cookie = document.cookie.split("; ").find(row => row.startsWith(name + "="));
  return cookie ? decodeURIComponent(cookie.split("=")[1]) : null;
}