//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.04.12 a las 12:06:14 PM CEST 
//


package servicio.tipos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emision" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="url" type="{http://www.um.es/programasRTVE}tipoUrl"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                 &lt;attribute name="tiempo" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;pattern value="[0-9][0-9]:([0-5][0-9]):[0-5][0-9]"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productoAmazon" type="{http://www.um.es/programasRTVE}productoAmazon" maxOccurs="3" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="urlPrograma" use="required" type="{http://www.um.es/programasRTVE}tipoUrl" />
 *       &lt;attribute name="urlImagen" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="http://img.rtve.es/imagenes/.*\.(png|jpg|gif)"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nombre",
    "emision",
    "productoAmazon"
})
@XmlRootElement(name = "programa")
public class Programa {

    @XmlElement(required = true)
    protected String nombre;
    protected List<Programa.Emision> emision;
    protected List<ProductoAmazon> productoAmazon;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "urlPrograma", required = true)
    protected String urlPrograma;
    @XmlAttribute(name = "urlImagen", required = true)
    protected String urlImagen;

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the emision property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emision property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmision().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Programa.Emision }
     * 
     * 
     */
    public List<Programa.Emision> getEmision() {
        if (emision == null) {
            emision = new ArrayList<Programa.Emision>();
        }
        return this.emision;
    }

    /**
     * Gets the value of the productoAmazon property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productoAmazon property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductoAmazon().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductoAmazon }
     * 
     * 
     */
    public List<ProductoAmazon> getProductoAmazon() {
        if (productoAmazon == null) {
            productoAmazon = new ArrayList<ProductoAmazon>();
        }
        return this.productoAmazon;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad urlPrograma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPrograma() {
        return urlPrograma;
    }

    /**
     * Define el valor de la propiedad urlPrograma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPrograma(String value) {
        this.urlPrograma = value;
    }

    /**
     * Obtiene el valor de la propiedad urlImagen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlImagen() {
        return urlImagen;
    }

    /**
     * Define el valor de la propiedad urlImagen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlImagen(String value) {
        this.urlImagen = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="url" type="{http://www.um.es/programasRTVE}tipoUrl"/>
     *       &lt;/sequence>
     *       &lt;attribute name="fecha" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
     *       &lt;attribute name="tiempo" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;pattern value="[0-9][0-9]:([0-5][0-9]):[0-5][0-9]"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "titulo",
        "url"
    })
    public static class Emision {

        @XmlElement(required = true)
        protected String titulo;
        @XmlElement(required = true)
        protected String url;
        @XmlAttribute(name = "fecha", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar fecha;
        @XmlAttribute(name = "tiempo", required = true)
        protected String tiempo;

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
         * Obtiene el valor de la propiedad url.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUrl() {
            return url;
        }

        /**
         * Define el valor de la propiedad url.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUrl(String value) {
            this.url = value;
        }

        /**
         * Obtiene el valor de la propiedad fecha.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFecha() {
            return fecha;
        }

        /**
         * Define el valor de la propiedad fecha.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFecha(XMLGregorianCalendar value) {
            this.fecha = value;
        }

        /**
         * Obtiene el valor de la propiedad tiempo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTiempo() {
            return tiempo;
        }

        /**
         * Define el valor de la propiedad tiempo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTiempo(String value) {
            this.tiempo = value;
        }

    }

}
