import React from "react";
import AddColorForm from "./AddColorForm4";

function onNewColor(title, color) {
  console.log(`==> ${title}, ${color}`);
}

export default function App() {
  return <AddColorForm onNewColor={onNewColor} />;
}
