@echo off
echo Starting Member Card Generator...
echo.

REM Navigate to tools directory (if needed)
cd tools

REM Compile the Java file
javac MemberCardGenerator.java

REM Check if compilation was successful
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b %errorlevel%
)

REM Run the program
java MemberCardGenerator

REM Show completion message
echo.
echo ========================================
echo Member cards updated successfully!
echo ========================================
echo.
pause