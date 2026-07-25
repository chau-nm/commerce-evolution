#!/bin/sh
# Continuously recompile source files in the background.
# DevTools watches build/classes and restarts the app when .class files change.
./gradlew -t classes --no-daemon &
./gradlew bootRun --no-daemon
