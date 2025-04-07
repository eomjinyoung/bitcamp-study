import React, { useState } from "react";

// 제어되는 컴포넌트(controlled component)
function AddColorForm() {
  const [title, setTitle] = useState("");
  const [color, setColor] = useState("#000000");

  const submit = (e) => {
    e.preventDefault();
    console.log(`${title}, ${color}`);
    setTitle("");
    setColor("#000000");
  };

  return (
    <form onSubmit={submit}>
      <input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        type='text'
        placeholder='color title...'
        required
      />
      <input value={color} onChange={(e) => setColor(e.target.value)} type='color' required />
      <button>추가</button>
    </form>
  );
}

export default AddColorForm;
