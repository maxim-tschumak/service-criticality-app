#!/bin/bash
set -e

psql --username "$POSTGRES_USER" architectures < /var/tmp/sample-architectures.sql
psql --username "$POSTGRES_USER" metrics < /var/tmp/sample-metrics.sql
