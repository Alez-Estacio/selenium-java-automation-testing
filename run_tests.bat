@echo off
setlocal enabledelayedexpansion

title Serenity BDD Test Runner
color 0A

:menu
cls
echo ====================================
echo     Serenity BDD Test Runner
echo ====================================
echo 1. Run FormPage Test (Serenity)
echo 2. Clean and Run All Tests
echo 3. Generate Reports Only
echo 4. Open Last Report
echo 5. Exit
echo ====================================
set /p choice=Choose an option (1-5): 

if "%choice%"=="1" (
    set "test=FormTest"
    goto run_test
)
if "%choice%"=="2" (
    set "clean=yes"
    goto run_all
)
if "%choice%"=="3" goto generate_reports
if "%choice%"=="4" goto open_report
if "%choice%"=="5" goto exit

echo Invalid option. Please choose a number between 1 and 5.
pause
goto menu

:run_test
cls
echo Running Serenity test: !test!
echo ------------------------------------
call mvn clean verify -Dtest=!test!
goto check_result

:run_all
cls
if defined clean (
    echo Running ALL tests with clean...
    echo ------------------------------------
    call mvn clean verify
) else (
    echo Running ALL tests...
    echo ------------------------------------
    call mvn verify
)
goto check_result

:generate_reports
cls
echo Generating Serenity reports...
echo ------------------------------------
call mvn serenity:aggregate
goto check_result

:open_report
cls
echo Opening last Serenity report...
echo ------------------------------------
start "" "target\site\serenity\index.html"
goto menu

:check_result
if %errorlevel% equ 0 (
    echo.
    echo TEST EXECUTION SUCCESSFUL
    echo Reports available at: target\site\serenity\index.html
) else (
    echo.
    echo TEST EXECUTION FAILED (Error: %errorlevel%)
)
pause
set "test="
set "clean="
goto menu

:exit
echo Exiting Serenity BDD Test Runner...
pause
exit