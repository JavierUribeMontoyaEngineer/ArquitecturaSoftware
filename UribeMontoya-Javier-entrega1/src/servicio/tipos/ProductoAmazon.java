//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.04.12 a las 12:06:14 PM CEST 
//


package servicio.tipos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Clase Java para productoAmazon complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="productoAmazon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ASIN" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="titulo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="smallImage" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="largeImage" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="precioMasBajo" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *       &lt;attribute name="urlDetallesProducto" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productoAmazon")
public class ProductoAmazon {

    @XmlAttribute(name = "ASIN", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String asin;
    @XmlAttribute(name = "titulo", required = true)
    protected String titulo;
    @XmlAttribute(name = "smallImage", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String smallImage;
    @XmlAttribute(name = "largeImage", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String largeImage;
    @XmlAttribute(name = "precioMasBajo", required = true)
    protected float precioMasBajo;
    @XmlAttribute(name = "urlDetallesProducto", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String urlDetallesProducto;

    /**
     * Obtiene el valor de la propiedad asin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASIN() {
        return asin;
    }

    /**
     * Define el valor de la propiedad asin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASIN(String value) {
        this.asin = value;
    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Obtiene el valor de la propiedad smallImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallImage() {
        return smallImage;
    }

    /**
     * Define el valor de la propiedad smallImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallImage(String value) {
        this.smallImage = value;
    }

    /**
     * Obtiene el valor de la propiedad largeImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLargeImage() {
        return largeImage;
    }

    /**
     * Define el valor de la propiedad largeImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLargeImage(String value) {
        this.largeImage = value;
    }

    /**
     * Obtiene el valor de la propiedad precioMasBajo.
     * 
     */
    public float getPrecioMasBajo() {
        return precioMasBajo;
    }

    /**
     * Define el valor de la propiedad precioMasBajo.
     * 
     */
    public void setPrecioMasBajo(float value) {
        this.precioMasBajo = value;
    }

    /**
     * Obtiene el valor de la propiedad urlDetallesProducto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlDetallesProducto() {
        return urlDetallesProducto;
    }

    /**
     * Define el valor de la propiedad urlDetallesProducto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlDetallesProducto(String value) {
        this.urlDetallesProducto = value;
    }

}
