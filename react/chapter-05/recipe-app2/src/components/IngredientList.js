import React from "react";

function Ingredient({ name, amount, measurement }) {
  return (
    <li>
      {name} {amount} {measurement}
    </li>
  );
}

export default function IngredientList({ list }) {
  return (
    <ul>
      {list.map((ingredient, index) => (
        <Ingredient key={index} {...ingredient} />
      ))}
    </ul>
  );
}
