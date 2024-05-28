import { useNavigate } from "react-router-dom";
import {atom, useAtom} from 'jotai'
import axios from "axios";

export const licensePlateAtom = atom('');
export const clientSecretAtom = atom('')

const SearchBar = () => {
    const [licensePlate, setLicensePlate] = useAtom(licensePlateAtom)
    const [clientSecret, setClientSecret] = useAtom(clientSecretAtom)
    const navigate = useNavigate();

    const handleInputChange = (event) => {
        setLicensePlate(event.target.value);
    };

    const startCheckout = async (e) =>{
        e.preventDefault();
        determineCheckoutCost();
        navigate('/order/checkout');
    }
    
    function determineCheckoutCost(){
        axios.post('http://localhost:8080/order/checkout', {
            licensePlate: licensePlate
        }, {
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
            }
        })
            .then(function (response) {
                setClientSecret(response.data.clientSecret);
            })
            .catch(function (error) {
                console.log('Error fetching data:', error);
            })

    }
    
  return (
    <form className="max-w-md mx-auto">
      <label
        htmlFor="default-search"
        className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white"
      >
        Search
      </label>
      <div className="relative">
        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
          <svg
            className="w-4 h-4 text-gray-500 dark:text-gray-400"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 20 20"
          >
            <path
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
            />
          </svg>
        </div>
        <input
          type="search"
          id="default-search"
          className="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="Enter License Plate"
          required
          onChange={handleInputChange}
          value={licensePlate}
        />
          {licensePlate.length > 0 && (
              <button
                  type="submit"
                  className="text-white absolute end-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  onClick={startCheckout}
              >
                  Pay
              </button>
          )}

      </div>
    </form>
  );
};

export default SearchBar;
