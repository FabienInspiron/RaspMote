cd ThirdPartServer/
cd src
wsimport -keep http://$1:$2/ws/raspberry?wsdl
cd ..
ant clean
ant

cd jar
ant
cd ..

ant run
