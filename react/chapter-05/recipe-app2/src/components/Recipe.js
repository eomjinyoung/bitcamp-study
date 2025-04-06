import React from 'react';
import Instructions from './Instructions';
import IngredientsList from './IngredientsList';

function Recipe({name, ingredients, steps}) {
  return (
    <section id="baked-salmon">
      <h2>{name}</h2>
      <IngredientsList list={ingredients} />
      <Instructions title={name} steps={steps} />
    </section>
  );
}

export default Recipe;