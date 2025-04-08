import React, { useState } from "react";
import colorData from "./color-data";
import ColorList from "../ColorList";
import AddColorForm from "./AddColorForm5";
import { v4 } from "uuid";

function onNewColor(title, color) {
  console.log(`5==> ${title}, ${color}`);
}

// useState 훅을 사용하여 색상 저장하기
export default function App() {
  const [colors, setColors] = useState(colorData);
  console.log(colors);
  return (
    <>
      <AddColorForm 
        onNewColor={(title, color) => {
          const newColors = [
            ...colors,
            {
              id: v4(),
              rating: 0,
              title,
              color
            }
          ];
          setColors(newColors);
        }} 
      />
      <ColorList 
        colors={colors} 
        onRemoveColor={id => {
          const newColors = colors.filter(color => color.id !== id);
          setColors(newColors);
        }}
        onRateColor={(id, rating) => {
          console.log(id, rating);
          const newColors = colors.map(color => 
            color.id === id ? { ...color, rating } : color
          );
          setColors(newColors);
        }}
      />
    </>
  );
}
