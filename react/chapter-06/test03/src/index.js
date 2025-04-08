import React from "react";
import { render } from "react-dom";
import ColorProvider from "./components/ColorProvider";
import App from "./components/App";

// 커스텀 훅 적용하기
render(
  <ColorProvider>
    <App />
  </ColorProvider>,
  document.getElementById("root")
);
