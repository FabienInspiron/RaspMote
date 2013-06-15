
package ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for full_outlet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="full_outlet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws/}outlet">
 *       &lt;sequence>
 *         &lt;element name="comNb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nbId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "full_outlet", propOrder = {
    "comNb",
    "nbId"
})
public class FullOutlet
    extends Outlet
{

    protected String comNb;
    protected String nbId;

    /**
     * Gets the value of the comNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComNb() {
        return comNb;
    }

    /**
     * Sets the value of the comNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComNb(String value) {
        this.comNb = value;
    }

    /**
     * Gets the value of the nbId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbId() {
        return nbId;
    }

    /**
     * Sets the value of the nbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbId(String value) {
        this.nbId = value;
    }

}
