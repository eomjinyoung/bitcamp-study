import React from "react";
import { render } from "react-dom";
import ColorProvider from "./components/ColorProvider2";
import App from "./components/App";

// 컨텍스트 프로바이더 만들기
render(
  <ColorProvider>
    <App />
  </ColorProvider>,
  document.getElementById("root")
);
