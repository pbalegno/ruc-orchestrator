#!/bin/sh

echo "The application will start in ${STARTING_SLEEP}s..." && sleep ${STARTING_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.guru.ruc.orchestrator.OrchestratorApplication"  "$@"
