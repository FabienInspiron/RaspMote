cd Client
cd src
wsimport -keep http://$1:$2/ws/thirdpartpublisher?wsdl
cd ..
ant clean
ant 
ant Client
