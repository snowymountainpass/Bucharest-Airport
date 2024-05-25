import SearchBar from "../Component/SearchBar.jsx";

const HeroSection = () => {
  return (
    <section className="bg-white dark:bg-gray-500">
      <div className="py-8 px-4 mx-auto max-w-screen-xl text-center lg:py-16">
        <h1 className="mb-4 text-4xl font-extrabold tracking-tight leading-none text-gray-900 md:text-5xl lg:text-6xl dark:text-white">
          Airport Parking Platform
        </h1>
        <p className="mb-8 text-lg font-normal text-gray-500 lg:text-xl sm:px-16 lg:px-48 dark:text-gray-400">
          The price per hour for a parking space is 5 Euro. Enter your license
          plate below in order to pay.
        </p>
        <div className="flex flex-col space-y-4 sm:flex-row sm:justify-center sm:space-y-0"></div>
        <SearchBar/>
      </div>
    </section>
  );
};

export default HeroSection;
