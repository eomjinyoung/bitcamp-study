export default function MemberLayout({ children }) {
  return (
    <html>
      <head>
        <meta charSet='UTF-8' />
      </head>
      <body>
        <hr />
        {children}
        <hr />
      </body>
    </html>
  );
}
