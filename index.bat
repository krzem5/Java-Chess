echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/chess/Main.java&&java com/krzem/chess/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"