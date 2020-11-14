--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.6
-- Dumped by pg_dump version 9.4.6
-- Started on 2020-11-13 15:51:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2013 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 16418)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE books (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    author character varying(50) NOT NULL,
    publisher character varying(50) NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE books OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16416)
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE books_id_seq OWNER TO postgres;

--
-- TOC entry 2014 (class 0 OID 0)
-- Dependencies: 173
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE books_id_seq OWNED BY books.id;


--
-- TOC entry 176 (class 1259 OID 16427)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    name character varying(200) NOT NULL,
    passwd character varying(500) NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16425)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- TOC entry 2015 (class 0 OID 0)
-- Dependencies: 175
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 1889 (class 2604 OID 16421)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books ALTER COLUMN id SET DEFAULT nextval('books_id_seq'::regclass);


--
-- TOC entry 1890 (class 2604 OID 16430)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 2003 (class 0 OID 16418)
-- Dependencies: 174
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY books (id, name, author, publisher, price) FROM stdin;
5	Introduction to Algorithms	Thomas H. Cormen	Publisher 5	120000
8	Buku 3	Author 3	Publisher 3	80000
9	Buku 4	Author 4	Publisher 4	80000
10	Buku 5	Author 5	Publisher 5	80000
11	Buku 6	Author 6	Publisher 6	80000
12	Buku 7	Author 7	Publisher 7	80000
13	Buku 8	Author 8	Publisher 8	80000
14	Buku 9	Author 9	Publisher 9	80000
15	Buku 10	Author 10	Publisher 10	80000
16	Buku 11	Author 11	Publisher 11	80000
17	Buku 12	Author 12	Publisher 12	80000
18	Buku 13	Author 13	Publisher 13	80000
19	Buku 14	Author 14	Publisher 14	80000
20	Buku 15	Author 15	Publisher 15	80000
21	Buku 16	Author 16	Publisher 16	80000
22	Buku 17	Author 17	Publisher 17	80000
23	Buku 18	Author 18	Publisher 18	80000
25	Buku 20	Author 20	Publisher 20	80000
24	Buku 19	Author 19	Publisher 191	100000
26	Buku 19	Author 19	Publisher 191	100000
36	Buku Baru	Yandi AR	Publisher 2	120000
4	The Pragmatic Programmer	Andrew Hunt and Dave Thomas	Publisher 2	500000
41	Buku Lagi	McDonals	Publisher 3	90000
42	Buku Hello World	Tukang Coding	Publisher 2	250000
43	Buku Tutorial Menjadi Programmer	Tukang Coding	Publisher 3	35000
6	Buku Keren	Author 1	Publisher 1	80000
3	Negeri Para Bedebah	Tere Liye	Publisher 1	180001
\.


--
-- TOC entry 2016 (class 0 OID 0)
-- Dependencies: 173
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('books_id_seq', 43, true);


--
-- TOC entry 2005 (class 0 OID 16427)
-- Dependencies: 176
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, email, name, passwd) FROM stdin;
1	user1@mail.com	User 1	0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90
2	user2@mail.com	user 2	6025d18fe48abd45168528f18a82e265dd98d421a7084aa09f61b341703901a3
\.


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 175
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 2, true);


--
-- TOC entry 1892 (class 2606 OID 16435)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2020-11-13 15:51:49

--
-- PostgreSQL database dump complete
--

