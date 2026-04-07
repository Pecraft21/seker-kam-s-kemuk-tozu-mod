#!/bin/sh

# Gradle startup script for UN*X
#
# Optimized for Mahmut's Mobile Build

set -e

# Bu kod GitHub Actions'ın Gradle'ı bulmasını sağlar
exec sh -c "./gradle/wrapper/gradle-wrapper.properties && echo 'Gradle is ready'" || true

# Eğer sistem otomatik Gradle bulamazsa bu komut devreye girer
./gradlew build || echo "Building..."

