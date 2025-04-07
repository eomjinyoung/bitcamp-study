import React from "react";
import AddColorForm from "./AddColorForm5";

function onNewColor(title, color) {
  console.log(`5==> ${title}, ${color}`);
}

export default function App() {
  return <AddColorForm onNewColor={onNewColor} />;
}
