import React, { useState, useEffect } from "react";

// useEffect() : 의존 관계 배열 사용 후
export default function App() {
  console.log("App() 랜더링!");

  const [val, setVal] = useState("");
  const [phrase, setPhrase] = useState("example phrase");

  const createPhrase = () => {
    setPhrase(val);
    setVal("");
  };

  useEffect(() => {
    console.log(`typing "${val}"`);
  }, [val]);

  useEffect(() => {
    console.log(`saved phrase: "${phrase}"`);
  }, [phrase]);

  // useEffect() : 의존 관계 배열 비워두기
  useEffect(() => {alert("딱 한 번만 호출됨!")}, []);

  // useEffect() : 의존 관계 배열 비워두기 + 함수 리턴하기
  useEffect(() => {
    alert("딱 한 번만 호출됨!222")
    return () => {alert("컴포넌트가 트리에서 제거될 때 딱 한 번 호출됨!")}
  }, []);

  return (
    <>
      <label>Favorite phrase:</label>
      <input 
        value={val}
        placeholder={phrase}
        onChange={e => setVal(e.target.value)}
      />
      <button onClick={createPhrase}> send</button>
    </>
  );
}
