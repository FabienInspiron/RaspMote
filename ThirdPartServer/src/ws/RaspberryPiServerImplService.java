
package ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "RaspberryPiServerImplService", targetNamespace = "http://ws/", wsdlLocation = "http://localhost:9999/ws/raspberry?wsdl")
public class RaspberryPiServerImplService
    extends Service
{

    private final static URL RASPBERRYPISERVERIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ws.RaspberryPiServerImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ws.RaspberryPiServerImplService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:9999/ws/raspberry?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:9999/ws/raspberry?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        RASPBERRYPISERVERIMPLSERVICE_WSDL_LOCATION = url;
    }

    public RaspberryPiServerImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RaspberryPiServerImplService() {
        super(RASPBERRYPISERVERIMPLSERVICE_WSDL_LOCATION, new QName("http://ws/", "RaspberryPiServerImplService"));
    }

    /**
     * 
     * @return
     *     returns IRaspberryPi
     */
    @WebEndpoint(name = "RaspberryPiServerImplPort")
    public IRaspberryPi getRaspberryPiServerImplPort() {
        return super.getPort(new QName("http://ws/", "RaspberryPiServerImplPort"), IRaspberryPi.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IRaspberryPi
     */
    @WebEndpoint(name = "RaspberryPiServerImplPort")
    public IRaspberryPi getRaspberryPiServerImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws/", "RaspberryPiServerImplPort"), IRaspberryPi.class, features);
    }

}