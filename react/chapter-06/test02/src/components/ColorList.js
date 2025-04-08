import React, { useContext } from 'react';
import { ColorContext } from './ColorProvider2';
import Color from './Color';

export default function ColorList() {
  const { colors } = useContext(ColorContext)
  console.log(colors);
  if (!colors.length) {
    return <div>표시할 색이 없습니다.</div>
  }

  return (
    <div className="color-list">
      {
        colors.map((color) => <Color key={color.id} {...color} />)
      }
    </div>
  );
}