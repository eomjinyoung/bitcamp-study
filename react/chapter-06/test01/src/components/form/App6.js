import React, { useState } from "react";
import colorData from "./color-data";
import AddColorForm from "./AddColorForm5";

function onNewColor(title, color) {
  console.log(`5==> ${title}, ${color}`);
}

export default function App() {
  const [colors, setColors] = useState(colorData);
  console.log(colors);
  return <AddColorForm onNewColor={onNewColor} />;
}
