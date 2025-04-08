import React, { useState, useEffect } from "react";

// useEffect() 사용하기
export default function Checkbox() {
  const [checked, setChecked] = useState(false);

  useEffect(() => {
    alert(`checked: ${checked.toString()}`);
  });

  return (
    <>
      <input type='checkbox' checked={checked} onChange={() => setChecked((checked) => !checked)} />
      {checked ? "checked" : "not checked"}
    </>
  );
}
