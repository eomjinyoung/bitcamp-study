import React from "react";

export default function Instructions({ title, steps }) {
  return (
    <section>
      <h2>{title}</h2>
      {steps.map((instruction, index) => (
        <p key={index}>{instruction}</p>
      ))}
    </section>
  );
}
