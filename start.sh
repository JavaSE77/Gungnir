#!/bin/bash
lxterminal -e "pi4j --run -Xmx2000M -Xms200M -XX:PermSize=256m -XX:MaxPermSize=512m -jar server.jar"