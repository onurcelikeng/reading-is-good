#!/bin/sh

set -e

echo "WORKING DIRECTORY: $(pwd)"

echo "1. Maven Clean"
mvn clean

echo "2. Packaging"
mvn package

echo "3. Docker building for reading-is-good"
docker build -t reading-is-good .

echo "4. SUCCESS"