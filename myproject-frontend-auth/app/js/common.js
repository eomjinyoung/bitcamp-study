const __restServer = "http://localhost:8010";
const __csrfToken = getCookie("XSRF-TOKEN");
let __jwtToken = localStorage.getItem("JWT-TOKEN");

document.addEventListener("DOMContentLoaded", () => {
  // DOM Tree 를 완성한 후, 렌더링 전에 호출됨
  loadHeader();
  loadFooter();

  if (__jwtToken) {
    getUserInfo();
  }
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

function getUserInfo() { // 페이지 푸터 로딩
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${__restServer}/auth/user-info`, false);
  xhr.setRequestHeader("Authorization", "Bearer " + __jwtToken);
  
  // 클라이언트에서 Cross Domain 으로 쿠키, 세션, HTTP 인증 헤더를 보내고 받고 싶다면, 다음을 설정해야 한다.
  // 설정하지 않으면, Cross Domain으로 쿠키, 세션, HTTP 인증 헤더를 보내거나 받을 수 없다.
  xhr.withCredentials = true;

  xhr.send();

  const result = JSON.parse(xhr.responseText);
  console.log(result);

  if (result.status == "success") {
    document.querySelector("#user-name").innerHTML = result.data.name;
    document.querySelector(".logged-out").classList.add("invisible");
    document.querySelector(".logged-in").classList.remove("invisible");
  } else {
    document.querySelector(".logged-in").classList.add("invisible");
    document.querySelector(".logged-out").classList.remove("invisible");
  }
}

function logout() {
  localStorage.removeItem("JWT-TOKEN");
  __jwtToken = null;
  location.href = "/home.html";
}

function getCookie(name) {
  const cookies = document.cookie.split('; ');
  for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split('=');
      if (cookieName === name) {
          return decodeURIComponent(cookieValue);
      }
  }
  return null; // 해당 쿠키가 없으면 null 반환
}
