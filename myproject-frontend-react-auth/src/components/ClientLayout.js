"use client";

import Header from "components/Header";
import Footer from "components/Footer";
import AuthProvider from "context/AuthProvider";

export default function ClientLayout({ children }) {
  return (
    <AuthProvider>
      <Header />
      <main className='page-content'>{children}</main>
      <Footer />
    </AuthProvider>
  );
}
