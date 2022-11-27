#!/bin/bash

cd $(dirname "$0")
./packLinuxExecutable.sh
./packWindowsExecutable.sh
./packMacExecutable.sh
