import React from "react";
import Star from "./Star";

export default function StarRating({ totalStars = 5 }) {
  //const stars = Array(totalStars).fill(undefined);
  // const stars = [...Array(totalStars)];
  // const list = stars.map((value) => <Star selected={true} />);
  // return list;
  return [...Array(totalStars)].map((v, i) => <Star key={i} selected={true} />);
}
