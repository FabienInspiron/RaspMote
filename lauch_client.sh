cd Client
cd src
wsimport -keep http://localhost.org:9998/ws/thirdpartpublisher?wsdl
cd ..
ant clean
ant 
ant Client
