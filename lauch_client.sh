cd Client
cd src
wsimport -keep http://$1:$2/ws/thirdpartpublisher?wsdl
cd ..
ant clean
ant
cd jar
ant
cd ..
ant Client

