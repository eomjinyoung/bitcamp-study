import { createContext, useContext } from "react";

export const ColorContext = createContext();
export const useColors = () => useContext(ColorContext);