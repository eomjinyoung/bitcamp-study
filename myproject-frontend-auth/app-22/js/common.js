const __csrfToken = getCookie("XSRF-TOKEN");
//let __jwtToken = localStorage.getItem("JWT-TOKEN");
let __jwtToken = getCookie("jwt_token");
//console.log("__jwtToken ===> ", __jwtToken);

document.addEventListener("DOMContentLoaded", () => {
  // DOM Tree 를 완성한 후, 렌더링 전에 호출됨
  loadHeader();
  loadFooter();
});

function loadHeader() {
  // 서버에 요청하는 일을 시작시키고, 
  // 서버의 결과가 올 때까지 기다리지 않고 즉시 그 작업에 대한 약속을 리턴한다.
  let promise1 = window.fetch("http://localhost:3010/header.html");
  console.log("첫 번째 작업 약속을 잡았다!");

  // 이전 약속이 이행되었을 떄 다음에 해야할 약속을 잡는다.
  // 다음 작업이 실행될 떄까지 기다리지 않고 작업 대한 약속을 리턴 한다.
  let promise2 = promise1.then((response) => {
    console.log("두 번째 작업 시작!");
    return response.text();
  }); 
  console.log("두 번째 작업 약속을 잡았다!");

  // 이전 약속이 이행되면 다음에 해야 할 일을 약속 잡는다.
  // 응답 결과에서 꺼낸 텍스트 받아서 작업을 수행한다.
  promise2.then((result) => {
    console.log("세 번째 작업 시작!");
    const parser = new DOMParser();
    const doc = parser.parseFromString(result, "text/html");
    document.body.insertBefore(doc.querySelector("#page-header"), document.body.firstChild);

    if (__jwtToken) {
      getUserInfo();
    }
  });
  console.log("세 번째 작업 약속을 잡았다!");
}

function loadFooter() { // 페이지 푸터 로딩
  window
    .fetch("http://localhost:3010/footer.html")
    .then((response) => {
      console.log("HTTP 응답에서 데이터를 텍스트로 추출한다.");
      return response.text();
    })
    .then((result) => {
      console.log("추출한 데이터를 가지고 페이지에 붙인다.");
      const parser = new DOMParser();
      const doc = parser.parseFromString(result, "text/html");
      document.body.appendChild(doc.querySelector("#page-footer"));
    });
    console.log("꼬리말을 가져오는 작업에 대한 약속을 모두 잡았다!");
}

function getUserInfo() {
  fetch(`http://localhost:8010/auth/user-info`, {
    headers: {
      "Authorization": "Bearer " + __jwtToken
    }
  })
  .then((response) => {
    return response.json();
  })
  .then((result) => {
    console.log(result);
    if (result.status == "success") {
      document.querySelector("#user-name").innerHTML = result.data.name;
      document.querySelector(".logged-out").classList.add("invisible");
      document.querySelector(".logged-in").classList.remove("invisible");
    } else {
      document.querySelector(".logged-in").classList.add("invisible");
      document.querySelector(".logged-out").classList.remove("invisible");
    }
  });
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
