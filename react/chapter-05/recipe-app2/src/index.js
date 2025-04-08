import React from "react";
import ReactDOM from "react-dom";
import recipeData from "./data/recipe-data";
import Menu from "./components/Menu";

const title = "Delicious Recipes";

ReactDOM.render(<Menu title={title} recipes={recipeData} />, document.getElementById("root"));
