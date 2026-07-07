@echo off
REM Build script for krsHubSwap

echo Building krsHubSwap...

REM Create directories
if not exist build\classes mkdir build\classes

REM Compile Java files
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\manager\RangeConfig.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\manager\AliasConfig.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\manager\TeleportManager.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\listener\ChatListener.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\command\SwapCommand.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\client\KrshubswapClient.java
javac -d build\classes -sourcepath src\main\java src\main\java\ru\krosovok\krshubswap\KrsHubSwap.java

REM Create JAR
if not exist build\libs mkdir build\libs
cd build\classes
jar cvf ..\libs\krshubswap-1.0.0.jar ru\
cd ..\..

echo Build complete! JAR file: build\libs\krshubswap-1.0.0.jar
pause
