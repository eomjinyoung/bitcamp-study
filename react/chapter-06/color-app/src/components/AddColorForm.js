import React, { useRef } from "react";

export default function AddColorForm() {
  // useRef(연결할객체) ===> 리턴 값: { current: 연결할객체}
  const txtTitle = useRef(); // 리턴 값: { current: undefined }
  const hexColor = useRef(); // 리턴 값: { current: undefined }

  function submit(e) {
    e.preventDefault();
    console.log(txtTitle.current.value, hexColor.current.value);
    txtTitle.current.value = "";
    hexColor.current.value = "#000000";
  }

  // 태그에서 ref 속성에 useRef() 객체를 설정하면 current 변수에 태그 객체가 저장된다.
  // 예) <input ref={txtTitle} />  ===> txtTitle.current = input태그객체
  return (
    <form>
      <input ref={txtTitle} type='text' required />
      <input ref={hexColor} type='color' required />
      <button onClick={submit}>추가</button>
    </form>
  );
}
