import React, { useContext } from 'react';
import StarRating from './StarRating';
import { FaTrash } from 'react-icons/fa';
import { ColorContext } from './ColorProvider2';

export default function Color({id, title, color, rating}) {
  const { rateColor, removeColor } = useContext(ColorContext);

  return (
    <section>
      <h1>{title}</h1>
      <button onClick={() => removeColor(id)}>
        <FaTrash />
      </button>
      <div style={{ height: 50, backgroundColor: color}} />
      <StarRating 
        selectedStars={rating} 
        onRate={rating => {
          rateColor(id, rating)
        }}
      />
    </section>
  );
}