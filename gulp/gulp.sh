#!bin/bash
SCRIPT_DIR=$(dirname "${BASH_SOURCE[0]}")

cd $SCRIPT_DIR
npm i
gulp
GULP_PID=$!

function finish {
    kill $GULP_PID
}
trap finish EXIT