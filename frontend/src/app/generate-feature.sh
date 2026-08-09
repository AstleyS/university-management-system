#!/bin/bash

FEATURE=$1

if [ -z "$FEATURE" ]; then
  echo "Usage: ./generate-feature.sh <feature_name>"
  exit 1
fi

ng generate service "features/$FEATURE/services/$FEATURE"
ng generate component "features/$FEATURE/components/$FEATURE-list"
ng generate interface "features/$FEATURE/models/$FEATURE"
