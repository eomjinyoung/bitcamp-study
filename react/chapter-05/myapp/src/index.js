import React from "react";
import { render } from "react-dom";
import ingredients from "../data/ingredients-data";
import App from "./components/App";

function clicked(name, content) {
  alert(`${name}님 ${content}!`);
}

render(
  <App content='안녕!! 리액트!' name='홍길동' onClick={clicked} />,
  document.getElementById("root")
);
