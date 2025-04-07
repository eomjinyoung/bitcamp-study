import React, { useRef } from "react";

// useRef() 사용 전
function AddColorForm() {
  const submit = (e) => {
    e.preventDefault();
    console.log("눌렀음!");
  };

  return (
    <form onSubmit={submit}>
      <input type='text' placeholder='color title...' required />
      <input type='color' required />
      <button>추가</button>
    </form>
  );
}

export default AddColorForm;
