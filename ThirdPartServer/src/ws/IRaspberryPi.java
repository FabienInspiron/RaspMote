
package ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import net.java.dev.jaxb.array.IntArray;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IRaspberryPi", targetNamespace = "http://ws/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ws.ObjectFactory.class,
    net.java.dev.jaxb.array.ObjectFactory.class
})
public interface IRaspberryPi {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(partName = "return")
    public int subscribe(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        int arg1);

    /**
     * 
     * @param arg0
     */
    @WebMethod(operationName = "switch_on")
    public void switchOn(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod(operationName = "switch_off")
    public void switchOff(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    public void setTimer(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0,
        @WebParam(name = "arg1", partName = "arg1")
        int arg1,
        @WebParam(name = "arg2", partName = "arg2")
        int arg2);

    /**
     * 
     * @return
     *     returns ws.OutletArray
     */
    @WebMethod
    @WebResult(partName = "return")
    public OutletArray getListOutlet();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String getListOutletXML();

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    public void setPresenceSimulator(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0,
        @WebParam(name = "arg1", partName = "arg1")
        int arg1);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    public void stopPresenceSimulator(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    public void switchOutlet(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns float
     */
    @WebMethod
    @WebResult(partName = "return")
    public float getTimer(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(partName = "return")
    public boolean isPresence(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    public void stopTimer(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @return
     *     returns net.java.dev.jaxb.array.IntArray
     */
    @WebMethod
    @WebResult(partName = "return")
    public IntArray getListTimer();

    /**
     * 
     * @return
     *     returns net.java.dev.jaxb.array.IntArray
     */
    @WebMethod
    @WebResult(partName = "return")
    public IntArray getListPresence();

}
