#!/bin/bash

TOMCAT="/opt/acepta/tomcat7/tomcat-datomed"
TOMCAT_BIN=${TOMCAT}"/bin"
TOMCAT_LOG=${TOMCAT}"/logs"
WEBAPP="/opt/acepta"
PROYECTO="datomed"
VERSION="1.0-SNAPSHOT"

echo "Bajando tomcat"
${TOMCAT_BIN}/tomcatctl stop

echo "compilando ...."
mvn clean package -Dmaven.test.skip=true

echo "eliminando compilacion anterior"
rm -rf ${WEBAPP}/${PROYECTO}
rm -rf ${TOMCAT}/work/*
rm -rf ${TOMCAT}/temp/*

echo "descomprimiendo lo compilado"
tar -C / -xzf target/${PROYECTO}-app-${VERSION}-web.tar.gz

echo "subiendo tomcat"
${TOMCAT_BIN}/tomcatctl start && tail -f ${TOMCAT_LOG}/catalina.out
