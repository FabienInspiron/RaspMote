cd Client
cd src
wsimport -keep http://localhost:9998/ws/thirdpartpublisher?wsdl
cd ..
ant clean
ant 
ant run
