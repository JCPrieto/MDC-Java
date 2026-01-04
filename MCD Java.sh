#!/usr/bin/env sh
set -e

script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
jar_path=$(ls -1t "$script_dir"/MCDJava-*.jar 2>/dev/null | head -n 1)

if [ -z "$jar_path" ]; then
    echo "No se encontró ningún JAR MCDJava-*.jar en $script_dir" >&2
    exit 1
fi

exec java -jar "$jar_path"
