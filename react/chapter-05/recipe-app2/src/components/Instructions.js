import React from 'react';

export default function Instructions({ title, steps }) {
  return (
    <section className='instructions'>
    <h2>{title}</h2>
      {steps.map((step, index) => (
        <p key={index}>{step}</p>
      ))}
    </section>
  );
}