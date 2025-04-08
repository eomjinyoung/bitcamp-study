import React from "react";
import Recipe from "./Recipe";

export default function Menu({ title, recipes }) {
  return (
    <article>
      <header>
        <h1>{title}</h1>
      </header>
      <div className='recipes'>
        {recipes.map(function (recipe) {
          return <Recipe key={recipe.name.toLowerCase().replace(/ /, "-")} {...recipe} />;
        })}
      </div>
    </article>
  );
}
