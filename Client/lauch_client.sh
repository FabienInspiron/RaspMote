cd Client
ant clean
ant 
cd src
wsimport -keep http://localhost:9998/ws/thirdpartpublisher?wsdl
cd ..
ant run
