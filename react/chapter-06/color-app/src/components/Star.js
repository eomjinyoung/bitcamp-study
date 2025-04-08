import React from "react";
import { FaStar } from "react-icons/fa";

export default function Star({ selected }) {
  console.log(selected);
  return <FaStar color={selected ? "red" : "gray"} />;
}
