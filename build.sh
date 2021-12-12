#!/bin/sh

# exit when any command fails
set -e

echo "WORKING DIRECTORY: $(pwd)"

echo "1. Maven Clean"
mvn clean

echo "2. Packaging"
mvn package

echo "3. Docker building for reading-is-good"
docker build -t reading-is-good-0.0.1-snapshot.jar .

echo "4. SUCCESS"