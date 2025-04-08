import React, { useState } from "react";
import Star from "./Star";

export default function StarRating({ totalStars = 5 }) {
  const [x, f] = useState(3);
  return [...Array(totalStars)].map((v, i) => {
    return <Star key={i} selected={i < x} />;
  });
}
