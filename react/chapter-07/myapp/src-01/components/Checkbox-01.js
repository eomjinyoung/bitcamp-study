import React, { useState } from "react";

// useEffect() 사용 전
export default function Checkbox() {
  const [checked, setChecked] = useState(false);

  alert(`checked: ${checked.toString()}`);

  return (
    <>
      <input type='checkbox' value={checked} onChange={() => setChecked((checked) => !checked)} />
      {checked ? "checked" : "not checked"}
    </>
  );
}
