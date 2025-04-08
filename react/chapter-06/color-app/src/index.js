import React from "react";
import ReactDOM from "react-dom";
import StarRating from "./components/StarRating";
import AddColorForm from "./components/AddColorForm";

ReactDOM.render(
  <>
    <AddColorForm />
    <StarRating style={{ backgroundColor: "lightblue", border: "1px solid red" }} totalStars={6} />
  </>,
  document.getElementById("root")
);
