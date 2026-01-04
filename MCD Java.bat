@echo off
setlocal enabledelayedexpansion

set "script_dir=%~dp0"
set "jar_path="
for /f "delims=" %%f in ('dir /b /a:-d /o:-d "%script_dir%MCDJava-*.jar" 2^>nul') do (
    set "jar_path=%script_dir%%%f"
    goto run
)

if "%jar_path%"=="" (
    echo No se encontro ningun JAR MCDJava-*.jar en %script_dir%
    exit /b 1
)

:run
java -jar "%jar_path%"
