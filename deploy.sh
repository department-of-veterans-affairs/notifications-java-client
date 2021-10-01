#!/usr/bin/env bash

set -eo pipefail

source .env

mvn clean deploy
