#!/bin/bash

DEST_DIR="$(pwd)"

cd "$DEST_DIR"

cd ServiceProxyHTTP
javac *.java
rmiregistry 2000 &
java LancerService &

cd ../ServiceBDD/Service
javac -cp .:ojdbc11.jar:json-20131018.jar *.java
rmiregistry 1099  &
java -cp .:ojdbc11.jar:json-20131018.jar LancerServiceRestaurant &

cd ../../ServeurHTTP
javac *.java
java LancerServeurHTTP &

xdg-open "https://webetu.iutnc.univ-lorraine.fr/~zott1u/CHARPENTIER_ZOTT_LEMEUNIER_DZIEZUK_CarteNancy-main/CarteNancy_web/"
