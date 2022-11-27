#!/bin/bash

distDir="built/windowsExecutable"

cd $(dirname "$0")
if [[ -d ${distDir} ]]; then
    rm -rf ${distDir}
fi

if [[ ! -d reducedJre ]]; then
    ./generateJre.sh
fi

java -jar packr-all-4.0.0.jar \
    --platform windows64 --jdk reducedJre \
    --executable windowsDistBuild --classpath ../desktop/build/libs/*.jar \
    --mainclass com.moltenwolfcub.adventurerpg.DesktopLauncher --output ${distDir} \
    --removelibs --useZgcIfSupportedOs
