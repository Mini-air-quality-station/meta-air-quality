#!/bin/bash -e

/usr/bin/influxd &
PID=$!
echo $PID > /var/lib/influxdb/influxd.pid

PROTOCOL="http"
BIND_ADDRESS=$(influxd print-config --key-name http-bind-address)
TLS_CERT=$(influxd print-config --key-name tls-cert | tr -d '"')
TLS_KEY=$(influxd print-config --key-name tls-key | tr -d '"')
if [ -n "${TLS_CERT}" ] && [ -n "${TLS_KEY}" ]; then
  echo "TLS cert and key found -- using https"
  PROTOCOL="https"
fi
HOST=${BIND_ADDRESS%:*}
HOST=${HOST:-"localhost"}
PORT=${BIND_ADDRESS##*:}

set +e
ATTEMPTS=0
URL="$PROTOCOL://$HOST:$PORT/ready"
RESULT=$(curl -k -s -o /dev/null $URL -w %{http_code})
while [ "${RESULT:0:2}" != "20" ] && [ "${RESULT:0:2}" != "40" ]; do
  ATTEMPTS=$(($ATTEMPTS+1))
  echo "InfluxDB API at $URL unavailable after $ATTEMPTS attempts..."
  sleep 1
  RESULT=$(curl -k -s -o /dev/null $URL -w %{http_code})
done
echo "InfluxDB started"
set -e