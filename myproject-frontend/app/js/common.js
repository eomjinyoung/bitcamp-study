const __csrfToken = getCookie("XSRF-TOKEN");

document.addEventListener("DOMContentLoaded", () => {
  // DOM Tree 를 완성한 후, 렌더링 전에 호출됨
  loadHeader();
  loadFooter();

  getUserInfo();
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
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "/auth/user-info", false);
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
  xhr.open("POST", "/logout", false);
  xhr.setRequestHeader("X-XSRF-TOKEN", __csrfToken);
  xhr.send();
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
