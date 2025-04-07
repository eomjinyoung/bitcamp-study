import React from "react";
import { useInput } from "./custom-hook";
import { useColors } from "./color-hook";

// 커스텀 훅 사용하기
function AddColorForm() {
  const [titleProps, resetTitle] = useInput("");
  const [colorProps, resetColor] = useInput("#000000");
  const { addColor } = useColors();

  const submit = (e) => {
    e.preventDefault();
    addColor(titleProps.value, colorProps.value);
    resetTitle();
    resetColor();
  };

  return (
    <form onSubmit={submit}>
      <input {...titleProps} type='text' placeholder='color title...' required />
      <input {...colorProps} type='color' required />
      <button>추가</button>
    </form>
  );
}

export default AddColorForm;
