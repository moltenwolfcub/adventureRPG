#!/bin/bash

jreDir="reducedJre"

cd $(dirname "$0")
if [[ -d ${jreDir} ]]; then
    rm -rf ${jreDir}
fi

jlink --add-modules \
    java.base,java.desktop,jdk.unsupported,java.logging,java.prefs,java.xml,jdk.incubator.foreign \
    --output ${jreDir}
