import React, { useState, useEffect } from "react";

// useEffect() : 의존 관계 배열 사용 전
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
  });

  useEffect(() => {
    console.log(`saved phrase: "${phrase}"`);
  });

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
