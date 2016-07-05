--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: architecture_stacks_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA architecture_stacks_data;


ALTER SCHEMA architecture_stacks_data OWNER TO postgres;

SET search_path = architecture_stacks_data, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: architecture; Type: TABLE; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE TABLE architecture (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(250)
);


ALTER TABLE architecture_stacks_data.architecture OWNER TO postgres;

--
-- Name: architecture_id_seq; Type: SEQUENCE; Schema: architecture_stacks_data; Owner: postgres
--

CREATE SEQUENCE architecture_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE architecture_stacks_data.architecture_id_seq OWNER TO postgres;

--
-- Name: architecture_id_seq; Type: SEQUENCE OWNED BY; Schema: architecture_stacks_data; Owner: postgres
--

ALTER SEQUENCE architecture_id_seq OWNED BY architecture.id;


--
-- Name: dependency; Type: TABLE; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE TABLE dependency (
    id bigint NOT NULL,
    service_id bigint NOT NULL,
    depends_on_service_id bigint NOT NULL,
    architecture_id bigint NOT NULL
);


ALTER TABLE architecture_stacks_data.dependency OWNER TO postgres;

--
-- Name: dependency_id_seq; Type: SEQUENCE; Schema: architecture_stacks_data; Owner: postgres
--

CREATE SEQUENCE dependency_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE architecture_stacks_data.dependency_id_seq OWNER TO postgres;

--
-- Name: dependency_id_seq; Type: SEQUENCE OWNED BY; Schema: architecture_stacks_data; Owner: postgres
--

ALTER SEQUENCE dependency_id_seq OWNED BY dependency.id;


--
-- Name: schema_version; Type: TABLE; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE TABLE schema_version (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE architecture_stacks_data.schema_version OWNER TO postgres;

--
-- Name: service; Type: TABLE; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE TABLE service (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(250),
    architecture_id bigint NOT NULL
);


ALTER TABLE architecture_stacks_data.service OWNER TO postgres;

--
-- Name: service_id_seq; Type: SEQUENCE; Schema: architecture_stacks_data; Owner: postgres
--

CREATE SEQUENCE service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE architecture_stacks_data.service_id_seq OWNER TO postgres;

--
-- Name: service_id_seq; Type: SEQUENCE OWNED BY; Schema: architecture_stacks_data; Owner: postgres
--

ALTER SEQUENCE service_id_seq OWNED BY service.id;


--
-- Name: id; Type: DEFAULT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY architecture ALTER COLUMN id SET DEFAULT nextval('architecture_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY dependency ALTER COLUMN id SET DEFAULT nextval('dependency_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY service ALTER COLUMN id SET DEFAULT nextval('service_id_seq'::regclass);


--
-- Data for Name: architecture; Type: TABLE DATA; Schema: architecture_stacks_data; Owner: postgres
--

COPY architecture (id, name, description) FROM stdin;
3	arch-1	awesome architecture
4	GoodBuy Architecture Stack	Services used for Block Order business processes and their direct communication partners. Pre-/Re- Order services are not part of this system. Architecture implements microservices principles. GoodBuy is the biggest team in Wholesale department.
5	service-criticality-framework	Framework for Service Criticality Calculations
\.


--
-- Name: architecture_id_seq; Type: SEQUENCE SET; Schema: architecture_stacks_data; Owner: postgres
--

SELECT pg_catalog.setval('architecture_id_seq', 5, true);


--
-- Data for Name: dependency; Type: TABLE DATA; Schema: architecture_stacks_data; Owner: postgres
--

COPY dependency (id, service_id, depends_on_service_id, architecture_id) FROM stdin;
9	5	6	3
10	8	11	4
11	8	12	4
12	9	10	4
13	9	19	4
14	10	14	4
15	10	15	4
16	10	17	4
17	11	20	4
19	12	18	4
21	12	17	4
23	13	17	4
25	14	17	4
26	14	18	4
31	17	19	4
33	17	18	4
34	18	19	4
35	19	20	4
36	11	17	4
38	17	20	4
39	22	21	5
40	23	22	5
41	23	21	5
\.


--
-- Name: dependency_id_seq; Type: SEQUENCE SET; Schema: architecture_stacks_data; Owner: postgres
--

SELECT pg_catalog.setval('dependency_id_seq', 41, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: architecture_stacks_data; Owner: postgres
--

COPY schema_version (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	0	<< Flyway Schema Creation >>	SCHEMA	"architecture_stacks_data"	\N	postgres	2016-04-27 14:20:59.199103	0	t
2	1.0	initial schema	SQL	V1_0__initial_schema.sql	-911870755	postgres	2016-04-27 14:20:59.244624	33	t
\.


--
-- Data for Name: service; Type: TABLE DATA; Schema: architecture_stacks_data; Owner: postgres
--

COPY service (id, name, description, architecture_id) FROM stdin;
5	service-a	Service A	3
6	service-b	Service B	3
7	service-c	Service C	3
8	ismt	ISMT Tool	4
9	block-orders-frontend	Frontend for Block Orders	4
10	user-authorization	User Authorization Service	4
11	pom-service	Purchase Order Management Service	4
12	ismt-gateway	ISMT Gateway	4
13	bi	Business Intelligence	4
14	block-orders-gateway	Block Orders Gateway	4
15	user-service	User Service	4
19	master-data	Master Data Service	4
18	buying-articles-service	Buying Articles Service	4
17	block-orders-call-offs	Block Orders and Call Offs Service	4
20	purchasing-backend	Purchasing Backend aka Monolith	4
22	metrics	Metrics Service	5
23	front-end	Front-End for the Framework	5
21	architectures	Architecture Stacks Service	5
\.


--
-- Name: service_id_seq; Type: SEQUENCE SET; Schema: architecture_stacks_data; Owner: postgres
--

SELECT pg_catalog.setval('service_id_seq', 23, true);


--
-- Name: architecture_pkey; Type: CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY architecture
    ADD CONSTRAINT architecture_pkey PRIMARY KEY (id);


--
-- Name: dependency_pkey; Type: CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dependency
    ADD CONSTRAINT dependency_pkey PRIMARY KEY (id);


--
-- Name: schema_version_pk; Type: CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_pk PRIMARY KEY (installed_rank);


--
-- Name: service_pkey; Type: CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id);


--
-- Name: architecture_name_uidx; Type: INDEX; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX architecture_name_uidx ON architecture USING btree (name);


--
-- Name: dependency_service_relationship_uidx; Type: INDEX; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX dependency_service_relationship_uidx ON dependency USING btree (architecture_id, service_id, depends_on_service_id);


--
-- Name: schema_version_s_idx; Type: INDEX; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE INDEX schema_version_s_idx ON schema_version USING btree (success);


--
-- Name: service_name_uidx; Type: INDEX; Schema: architecture_stacks_data; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX service_name_uidx ON service USING btree (name);


--
-- Name: dependency_architecture_id_fkey; Type: FK CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY dependency
    ADD CONSTRAINT dependency_architecture_id_fkey FOREIGN KEY (architecture_id) REFERENCES architecture(id);


--
-- Name: dependency_depends_on_service_id_fkey; Type: FK CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY dependency
    ADD CONSTRAINT dependency_depends_on_service_id_fkey FOREIGN KEY (depends_on_service_id) REFERENCES service(id);


--
-- Name: dependency_service_id_fkey; Type: FK CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY dependency
    ADD CONSTRAINT dependency_service_id_fkey FOREIGN KEY (service_id) REFERENCES service(id);


--
-- Name: service_architecture_id_fkey; Type: FK CONSTRAINT; Schema: architecture_stacks_data; Owner: postgres
--

ALTER TABLE ONLY service
    ADD CONSTRAINT service_architecture_id_fkey FOREIGN KEY (architecture_id) REFERENCES architecture(id);


--
-- PostgreSQL database dump complete
--

