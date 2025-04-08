import React, { useRef } from "react";

export default function AddColorForm() {
  const txtTitle = useRef("오호라!");
  const hexColor = useRef("#ffff00");

  function submit(e) {
    e.preventDefault();
    console.log(txtTitle.current.value, hexColor.current.value);
    txtTitle.current.value = "";
    hexColor.current.value = "#000000";
  }

  return (
    <form>
      <input ref={txtTitle} type='text' required />
      <input ref={hexColor} type='color' required />
      <button onClick={submit}>추가</button>
    </form>
  );
}
