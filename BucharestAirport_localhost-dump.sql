--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: airport; Type: TABLE; Schema: public; Owner: vasilecipriangabriel
--

CREATE TABLE public.airport (
    id bigint NOT NULL,
    airport_code character varying(255),
    airport_cost_per_minute integer NOT NULL,
    airport_name character varying(255),
    number_of_occupied_parking_spaces integer NOT NULL,
    number_of_parking_spaces integer NOT NULL
);


ALTER TABLE public.airport OWNER TO vasilecipriangabriel;

--
-- Name: airport_sequence; Type: SEQUENCE; Schema: public; Owner: vasilecipriangabriel
--

CREATE SEQUENCE public.airport_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.airport_sequence OWNER TO vasilecipriangabriel;

--
-- Name: transaction; Type: TABLE; Schema: public; Owner: vasilecipriangabriel
--

CREATE TABLE public.transaction (
    id bigint NOT NULL,
    car_license_plate character varying(255),
    cost integer NOT NULL,
    departure_time timestamp(6) without time zone,
    entry_time timestamp(6) without time zone,
    transaction_is_paid boolean NOT NULL,
    airport_id bigint
);


ALTER TABLE public.transaction OWNER TO vasilecipriangabriel;

--
-- Name: transaction_sequence; Type: SEQUENCE; Schema: public; Owner: vasilecipriangabriel
--

CREATE SEQUENCE public.transaction_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transaction_sequence OWNER TO vasilecipriangabriel;

--
-- Data for Name: airport; Type: TABLE DATA; Schema: public; Owner: vasilecipriangabriel
--

COPY public.airport (id, airport_code, airport_cost_per_minute, airport_name, number_of_occupied_parking_spaces, number_of_parking_spaces) FROM stdin;
1	OTP	5	Bucharest International Airport	0	1000
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: vasilecipriangabriel
--

COPY public.transaction (id, car_license_plate, cost, departure_time, entry_time, transaction_is_paid, airport_id) FROM stdin;
\.


--
-- Name: airport_sequence; Type: SEQUENCE SET; Schema: public; Owner: vasilecipriangabriel
--

SELECT pg_catalog.setval('public.airport_sequence', 1, false);


--
-- Name: transaction_sequence; Type: SEQUENCE SET; Schema: public; Owner: vasilecipriangabriel
--

SELECT pg_catalog.setval('public.transaction_sequence', 13, true);


--
-- Name: airport airport_pkey; Type: CONSTRAINT; Schema: public; Owner: vasilecipriangabriel
--

ALTER TABLE ONLY public.airport
    ADD CONSTRAINT airport_pkey PRIMARY KEY (id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: vasilecipriangabriel
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- Name: transaction fkass67xs9s1qssrgltgbc6vmh2; Type: FK CONSTRAINT; Schema: public; Owner: vasilecipriangabriel
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT fkass67xs9s1qssrgltgbc6vmh2 FOREIGN KEY (airport_id) REFERENCES public.airport(id);


--
-- PostgreSQL database dump complete
--

