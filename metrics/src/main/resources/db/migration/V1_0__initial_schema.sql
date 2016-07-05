CREATE SCHEMA IF NOT EXISTS metrics_data;

CREATE TABLE metrics_data.metric (
  id                     BIGSERIAL PRIMARY KEY NOT NULL,
  name                   VARCHAR(50)           NOT NULL,
  service_id             BIGINT                NOT NULL,
  architecture_id        BIGINT                NOT NULL,
  value                  DOUBLE PRECISION      NOT NULL,
  normalized_value       BIGINT                NULL,
  additional_information VARCHAR(250)          NULL
);

CREATE UNIQUE INDEX unique_metric_value_per_service_uidx ON metrics_data.metric (name, service_id)