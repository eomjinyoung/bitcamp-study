const __csrfToken = getCookie("XSRF-TOKEN");
//let __jwtToken = localStorage.getItem("JWT-TOKEN");
let __jwtToken = getCookie("jwt_token");
//console.log("__jwtToken ===> ", __jwtToken);

document.addEventListener("DOMContentLoaded", () => {
  // DOM Tree 를 완성한 후, 렌더링 전에 호출됨
  loadHeader();
  loadFooter();
});

async function loadHeader() {
  const response = await axios({
    method: "get", // 기본값: "get"
    url: "http://localhost:3010/header.html",
    responseType: "text" // 기본값: "json"
  });
  const result = response.data;

  const parser = new DOMParser();
  const doc = parser.parseFromString(result, "text/html");
  document.body.insertBefore(doc.querySelector("#page-header"), document.body.firstChild);

  if (__jwtToken) {
    getUserInfo();
  }
}

async function loadFooter() { // 페이지 푸터 로딩
    const response = await axios.get("http://localhost:3010/footer.html", {
      responseType: "text"
    });
    const result =  response.data;
        
    const parser = new DOMParser();
    const doc = parser.parseFromString(result, "text/html");
    document.body.appendChild(doc.querySelector("#page-footer"));
}

async function getUserInfo() {
  const response = await axios.get(`http://localhost:8010/auth/user-info`, {
    headers: {
      "Authorization": "Bearer " + __jwtToken
    }
  });
  const result = response.data;
  
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
  //localStorage.removeItem("JWT-TOKEN");
  document.cookie = `jwt_token=; path=/; domain=localhost; expires=Thu, 01 Jan 1970 00:00:00 UTC; SameSite=None; Secure`;
  __jwtToken = null;
  location.href = "http://localhost:3010/home.html";
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
