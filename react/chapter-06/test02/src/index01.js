import React, { createContext } from "react";
import { render } from "react-dom";
import colors from "./components/color-data";
import App from "./components/App";

// 컨텍스트 사용하기
export const ColorContext = createContext();

render(
  <ColorContext.Provider value={ {colors} }>
    <App />
  </ColorContext.Provider>,
  document.getElementById("root")
);
