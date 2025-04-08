import React from "react";
import { FaStar } from "react-icons/fa";

export default function Star({ selected, onClick }) {
  return <FaStar color={selected ? "red" : "gray"} onClick={onClick} />;
}
