CREATE SCHEMA IF NOT EXISTS architecture_stacks_data;

CREATE TABLE architecture_stacks_data.architecture (
  id          BIGSERIAL PRIMARY KEY NOT NULL,
  name        VARCHAR(50)           NOT NULL,
  description VARCHAR(250)          NULL
);
CREATE UNIQUE INDEX architecture_name_uidx ON architecture_stacks_data.architecture (name);

CREATE TABLE architecture_stacks_data.service (
  id              BIGSERIAL PRIMARY KEY     NOT NULL,
  name            VARCHAR(50)               NOT NULL,
  description     VARCHAR(250)              NULL,
  architecture_id BIGINT                    NOT NULL,

  FOREIGN KEY (architecture_id) REFERENCES architecture_stacks_data.architecture (id)
);
CREATE UNIQUE INDEX service_name_uidx ON architecture_stacks_data.service (name);

CREATE TABLE architecture_stacks_data.dependency (
  id                    BIGSERIAL PRIMARY KEY NOT NULL,
  service_id            BIGINT                NOT NULL,
  depends_on_service_id BIGINT                NOT NULL,
  architecture_id       BIGINT                NOT NULL,

  FOREIGN KEY (architecture_id) REFERENCES architecture_stacks_data.architecture (id),
  FOREIGN KEY (service_id) REFERENCES architecture_stacks_data.service (id),
  FOREIGN KEY (depends_on_service_id) REFERENCES architecture_stacks_data.service (id)
);
CREATE UNIQUE INDEX dependency_service_relationship_uidx ON architecture_stacks_data.dependency (architecture_id, service_id, depends_on_service_id)