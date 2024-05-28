const Header = () => {
  return (
    <nav className="bg-white border-gray-200 dark:bg-gray-900">
      <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
        <a href="/" className="flex items-center space-x-3 rtl:space-x-reverse">
          <img
            src="src/assets/BucharestAirportsLogo.png"
            className="h-8"
            alt="Bucharest Airports Logo"
          />

          <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white text-wrap">
            Bucharest International Airport
          </span>
        </a>
      </div>
    </nav>
  );
};
// https://www.bucharestairports.ro/en
export default Header;
