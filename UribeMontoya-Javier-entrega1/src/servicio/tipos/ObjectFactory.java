//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.04.12 a las 12:06:14 PM CEST 
//


package servicio.tipos;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the servicio.tipos package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: servicio.tipos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Programa }
     * 
     */
    public Programa createPrograma() {
        return new Programa();
    }

    /**
     * Create an instance of {@link Programa.Emision }
     * 
     */
    public Programa.Emision createProgramaEmision() {
        return new Programa.Emision();
    }

    /**
     * Create an instance of {@link ProductoAmazon }
     * 
     */
    public ProductoAmazon createProductoAmazon() {
        return new ProductoAmazon();
    }

}
