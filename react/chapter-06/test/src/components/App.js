import React, { useState } from "react";
//import StarRating from "./StarRating";
import colorData from "./color-data";
import ColorList from "./ColorList";

export default function App() {
  const [colors, setColors] = useState(colorData.colors);
  return (
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
  );
}