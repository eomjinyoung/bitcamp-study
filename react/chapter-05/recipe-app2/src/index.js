import React from "react";
import ReactDOM from "react-dom";
import recipeData from "./data/recipe-data";
import Recipe from "./components/Recipe";

console.log(recipeData);

const title = "Delicious Recipes";

ReactDOM.render(
  <article>
    <header>
      <h1>{title}</h1>
    </header>
    <div className='recipes'>
      {recipeData.map(function (recipe) {
        return <Recipe key={recipe.name.toLowerCase().replace(/ /, "-")} {...recipe} />;
      })}
    </div>
  </article>,
  document.getElementById("root")
);
