#!/bin/bash

# Build script for krsHubSwap
echo "Building krsHubSwap..."

# Compile Java files
mkdir -p build/classes
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/manager/RangeConfig.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/manager/AliasConfig.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/manager/TeleportManager.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/listener/ChatListener.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/command/SwapCommand.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/client/KrshubswapClient.java
javac -d build/classes -sourcepath src/main/java src/main/java/ru/krosovok/krshubswap/KrsHubSwap.java

# Create JAR
mkdir -p build/libs
cd build/classes
jar cvf ../libs/krshubswap-1.0.0.jar ru/
cd ../..

echo "Build complete! JAR file: build/libs/krshubswap-1.0.0.jar"
