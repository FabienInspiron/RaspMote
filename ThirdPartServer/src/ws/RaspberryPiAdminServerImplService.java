
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
@WebServiceClient(name = "RaspberryPiAdminServerImplService", targetNamespace = "http://ws/", wsdlLocation = "http://192.168.1.17:9991/ws/raspberry?wsdl")
public class RaspberryPiAdminServerImplService
    extends Service
{

    private final static URL RASPBERRYPIADMINSERVERIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ws.RaspberryPiAdminServerImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ws.RaspberryPiAdminServerImplService.class.getResource(".");
            url = new URL(baseUrl, "http://192.168.1.17:9991/ws/raspberry?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://192.168.1.17:9991/ws/raspberry?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        RASPBERRYPIADMINSERVERIMPLSERVICE_WSDL_LOCATION = url;
    }

    public RaspberryPiAdminServerImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RaspberryPiAdminServerImplService() {
        super(RASPBERRYPIADMINSERVERIMPLSERVICE_WSDL_LOCATION, new QName("http://ws/", "RaspberryPiAdminServerImplService"));
    }

    /**
     * 
     * @return
     *     returns IRaspberryPiAdmin
     */
    @WebEndpoint(name = "RaspberryPiAdminServerImplPort")
    public IRaspberryPiAdmin getRaspberryPiAdminServerImplPort() {
        return super.getPort(new QName("http://ws/", "RaspberryPiAdminServerImplPort"), IRaspberryPiAdmin.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IRaspberryPiAdmin
     */
    @WebEndpoint(name = "RaspberryPiAdminServerImplPort")
    public IRaspberryPiAdmin getRaspberryPiAdminServerImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws/", "RaspberryPiAdminServerImplPort"), IRaspberryPiAdmin.class, features);
    }

}
