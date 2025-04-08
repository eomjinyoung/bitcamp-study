import React, { useState } from "react";
import Star from "./Star";

export default function StarRating({ style = {}, totalStars = 5 }) {
  const [selectedStar, setSelectedStar] = useState(3);

  return (
    <div style={{ padding: "10px", ...style }}>
      {[...Array(totalStars)].map((v, i) => (
        <Star key={i} selected={i < selectedStar} onClick={() => setSelectedStar(i + 1)} />
      ))}
      <p>
        {selectedStar} of {totalStars} stars
      </p>
    </div>
  );
}
