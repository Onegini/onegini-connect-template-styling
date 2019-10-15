#!/bin/bash
SCRIPT_DIR=$(dirname "${BASH_SOURCE[0]}")

# Catch exit and teminate processes
function finish {
    kill $GULP_PID
    kill $SERVER_PID
}
trap finish EXIT

# Source configuration
source $1

# Create config.json for this project
cat > $SCRIPT_DIR/gulp/config.json <<EOF
    {
        "port": 3000,
        "proxyPort": $PORT,
        "src": {
            "scss": "$GULP_SRC/static/scss/**/*.scss",
            "js": "$GULP_SRC/static/js/**/*.js",
            "html": "$GULP_SRC/templates/**/*.html"
        },
        "target": {
            "css": "$EXT_SRC/static/css/",
            "js": "$EXT_SRC/static/js/",
            "html": "$EXT_SRC/templates"
        }
    }
EOF

#Start Gulp
bash $SCRIPT_DIR/gulp/gulp.sh &
GULP_PID=$!

echo $SCRIPT_DIR

#Run webserver
mvn spring-boot:run -Dserver.port=$PORT -DSTYLING_EXTENSION_RESOURCES_LOCATION=$EXT_SRC -DSTYLING_CORE_RESOURCES_LOCATION=$CORE_SRC -DSTYLING_TEMPLATE_CONFIG_LOCATION=$YAML_SRC &
SERVER_PID=$!

# Create infinite loop to be able to trigger the finish function
while true
do
  sleep 1
done