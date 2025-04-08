import React from "react";
import ReactDOM from "react-dom";
import recipeData from "./data/recipe-data";

console.log(recipeData);

const title = "Delicious Recipes";

ReactDOM.render(
  <article>
    <header>
      <h1>{title}</h1>
    </header>
    <div className='recipes'>
      {recipeData.map(function (recipe) {
        return (
          <>
            <section key={recipe.name.toLowerCase().replace(/ /, "-")}>
              <h1>{recipe.name}</h1>
              <ul>
                {recipe.ingredients.map((ingredient, index) => (
                  <li key={index}>
                    {ingredient.name} {ingredient.amout} {ingredient.measurement}
                  </li>
                ))}
              </ul>
              <section>
                <h2>Cooking Inststructions</h2>
                {recipe.steps.map((instruction) => (
                  <p>{instruction}</p>
                ))}
              </section>
            </section>
            <hr />
          </>
        );
      })}
    </div>
  </article>,
  document.getElementById("root")
);
