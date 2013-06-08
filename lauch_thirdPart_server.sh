cd ThirdPartServer/
cd src
wsimport -keep http://localhost:9999/ws/raspberry?wsdl
cd ..
ant clean
ant
ant run
