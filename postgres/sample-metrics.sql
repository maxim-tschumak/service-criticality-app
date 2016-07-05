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
-- Name: metrics_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA metrics_data;


ALTER SCHEMA metrics_data OWNER TO postgres;

SET search_path = metrics_data, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: metric; Type: TABLE; Schema: metrics_data; Owner: postgres; Tablespace: 
--

CREATE TABLE metric (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    service_id bigint NOT NULL,
    architecture_id bigint NOT NULL,
    value double precision NOT NULL,
    normalized_value bigint,
    additional_information character varying(250)
);


ALTER TABLE metrics_data.metric OWNER TO postgres;

--
-- Name: metric_id_seq; Type: SEQUENCE; Schema: metrics_data; Owner: postgres
--

CREATE SEQUENCE metric_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE metrics_data.metric_id_seq OWNER TO postgres;

--
-- Name: metric_id_seq; Type: SEQUENCE OWNED BY; Schema: metrics_data; Owner: postgres
--

ALTER SEQUENCE metric_id_seq OWNED BY metric.id;


--
-- Name: schema_version; Type: TABLE; Schema: metrics_data; Owner: postgres; Tablespace: 
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


ALTER TABLE metrics_data.schema_version OWNER TO postgres;

--
-- Name: id; Type: DEFAULT; Schema: metrics_data; Owner: postgres
--

ALTER TABLE ONLY metric ALTER COLUMN id SET DEFAULT nextval('metric_id_seq'::regclass);


--
-- Data for Name: metric; Type: TABLE DATA; Schema: metrics_data; Owner: postgres
--

COPY metric (id, name, service_id, architecture_id, value, normalized_value, additional_information) FROM stdin;
137	effective-size	22	5	1	0	
128	in-degree	21	5	2	100	
129	in-degree	22	5	1	50	
144	in-degree	23	5	0	0	
64	constraint	5	3	1	100	
26	out-degree	11	4	2	66	
27	out-degree	12	4	2	66	
132	degree	21	5	2	0	
134	katz-centrality	21	5	2.25	100	
135	katz-centrality	22	5	1.5	40	
147	katz-centrality	23	5	1	0	
28	out-degree	13	4	1	33	
65	constraint	6	3	0	0	
142	test-coverage	21	5	0.949999999999999956	100	\N
133	degree	22	5	2	0	
1	in-degree	5	3	0	0	
146	degree	23	5	2	0	
61	effective-size	5	3	2	100	
159	lines-of-code	13	4	1000	0	\N
151	lines-of-code	9	4	13000	2	\N
160	lines-of-code	15	4	1000	0	\N
161	lines-of-code	19	4	1000	0	\N
62	effective-size	6	3	2	100	
157	lines-of-code	11	4	5800	0	\N
158	lines-of-code	20	4	563000	100	\N
162	lines-of-code	8	4	1000	0	\N
130	out-degree	21	5	0	0	
2	in-degree	6	3	1	100	
63	effective-size	7	3	1	0	
3	in-degree	7	3	0	0	
16	out-degree	5	3	1	100	
17	out-degree	6	3	0	0	
29	out-degree	14	4	2	66	
30	out-degree	15	4	0	0	
18	out-degree	7	3	0	0	
33	degree	7	3	0	0	
31	degree	5	3	1	100	
143	test-coverage	22	5	0.800000000000000044	0	\N
19	out-degree	17	4	3	100	
34	katz-centrality	5	3	1	0	
35	katz-centrality	6	3	1.5	100	
36	katz-centrality	7	3	1	0	
32	degree	6	3	1	100	
37	degree	17	4	8	100	
20	out-degree	18	4	1	33	
13	in-degree	13	4	0	0	
38	degree	18	4	4	42	
39	degree	19	4	4	42	
131	out-degree	22	5	1	50	
40	degree	20	4	3	28	
21	out-degree	19	4	1	33	
22	out-degree	20	4	0	0	
145	out-degree	23	5	2	100	
23	out-degree	8	4	2	66	
24	out-degree	9	4	2	66	
140	cycles	21	5	0	0	
25	out-degree	10	4	3	100	
14	in-degree	14	4	1	20	
141	cycles	22	5	0	0	
150	cycles	23	5	0	0	
148	effective-size	23	5	1	0	
136	effective-size	21	5	1	0	
138	constraint	21	5	0	0	
139	constraint	22	5	1	20	
149	constraint	23	5	5	100	
66	constraint	7	3	0	0	
73	effective-size	20	4	8.09090909090908994	28	
74	effective-size	8	4	7.90909090909090917	14	
7	in-degree	20	4	3	60	
8	in-degree	8	4	0	0	
9	in-degree	9	4	0	0	
125	test-coverage	5	3	0.5	0	\N
10	in-degree	10	4	1	20	
75	effective-size	9	4	7.90909090909090917	14	
76	effective-size	10	4	8.27272727272727337	42	
77	effective-size	11	4	8.09090909090908994	28	
92	constraint	14	4	5	55	
93	constraint	15	4	0	0	
82	constraint	17	4	9	100	
83	constraint	18	4	1	11	
84	constraint	19	4	1	11	
153	lines-of-code	12	4	1500	0	\N
152	lines-of-code	10	4	1000	0	\N
78	effective-size	12	4	8.09090909090908994	28	
79	effective-size	13	4	7.72727272727272663	0	
85	constraint	20	4	0	0	
4	in-degree	17	4	5	100	
163	lines-of-code	18	4	1000	0	\N
5	in-degree	18	4	3	60	
11	in-degree	11	4	1	20	
70	effective-size	17	4	9	100	
58	katz-centrality	13	4	1	0	
71	effective-size	18	4	8.27272727272727337	42	
59	katz-centrality	14	4	1.75	12	
12	in-degree	12	4	1	20	
15	in-degree	15	4	1	20	
45	degree	12	4	3	28	
46	degree	13	4	1	0	
51	katz-centrality	19	4	6.28125	85	
47	degree	14	4	3	28	
60	katz-centrality	15	4	1.75	12	
48	degree	15	4	1	0	
109	cycles	17	4	0	0	
110	cycles	18	4	0	0	
80	effective-size	14	4	8.09090909090908994	28	
49	katz-centrality	17	4	4.625	58	
6	in-degree	19	4	3	60	
81	effective-size	15	4	7.72727272727272663	0	
50	katz-centrality	18	4	4.9375	63	
126	test-coverage	6	3	0.599999999999999978	33	\N
52	katz-centrality	20	4	7.203125	100	
56	katz-centrality	11	4	1.5	8	
106	cycles	5	3	0	0	
53	katz-centrality	8	4	1	0	
54	katz-centrality	9	4	1	0	
127	test-coverage	7	3	0.800000000000000044	100	\N
72	effective-size	19	4	8.27272727272727337	42	
41	degree	8	4	2	14	
111	cycles	19	4	0	0	
86	constraint	8	4	2	22	
42	degree	9	4	2	14	
43	degree	10	4	4	42	
44	degree	11	4	3	28	
87	constraint	9	4	2	22	
88	constraint	10	4	6	66	
89	constraint	11	4	5	55	
57	katz-centrality	12	4	1.5	8	
90	constraint	12	4	5	55	
91	constraint	13	4	1	11	
55	katz-centrality	10	4	1.5	8	
107	cycles	6	3	0	0	
108	cycles	7	3	0	0	
154	lines-of-code	17	4	21000	3	\N
155	lines-of-code	14	4	6600	0	\N
112	cycles	20	4	0	0	
113	cycles	8	4	0	0	
114	cycles	9	4	0	0	
115	cycles	10	4	0	0	
116	cycles	11	4	0	0	
117	cycles	12	4	0	0	
118	cycles	13	4	0	0	
119	cycles	14	4	0	0	
120	cycles	15	4	0	0	
\.


--
-- Name: metric_id_seq; Type: SEQUENCE SET; Schema: metrics_data; Owner: postgres
--

SELECT pg_catalog.setval('metric_id_seq', 163, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: metrics_data; Owner: postgres
--

COPY schema_version (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	0	<< Flyway Schema Creation >>	SCHEMA	"metrics_data"	\N	postgres	2016-05-24 14:16:14.562871	0	t
2	1.0	initial schema	SQL	V1_0__initial_schema.sql	-898758650	postgres	2016-05-24 14:16:14.611732	22	t
\.


--
-- Name: metric_pkey; Type: CONSTRAINT; Schema: metrics_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY metric
    ADD CONSTRAINT metric_pkey PRIMARY KEY (id);


--
-- Name: schema_version_pk; Type: CONSTRAINT; Schema: metrics_data; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_pk PRIMARY KEY (installed_rank);


--
-- Name: schema_version_s_idx; Type: INDEX; Schema: metrics_data; Owner: postgres; Tablespace: 
--

CREATE INDEX schema_version_s_idx ON schema_version USING btree (success);


--
-- Name: unique_metric_value_per_service_uidx; Type: INDEX; Schema: metrics_data; Owner: postgres; Tablespace: 
--

CREATE UNIQUE INDEX unique_metric_value_per_service_uidx ON metric USING btree (name, service_id);


--
-- PostgreSQL database dump complete
--

