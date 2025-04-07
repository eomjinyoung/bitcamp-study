import React, { useRef } from "react";

// useRef() 사용 후
function AddColorForm() {
  const txtTitle = useRef(); // 리턴 값: { current: undefined }
  const hexColor = useRef(); // 리턴 값: { current: undefined }

  const submit = (e) => {
    e.preventDefault();
    console.log(`${txtTitle.current.value}, ${hexColor.current.value}`);
    txtTitle.current.value = "";
    hexColor.current.value = "#000000";
  };

  return (
    <form onSubmit={submit}>
      <input ref={txtTitle} type='text' placeholder='color title...' required />
      <input ref={hexColor} type='color' required />
      <button>추가</button>
    </form>
  );
}

export default AddColorForm;
