package application;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDateTime;

public class Bill {

    private static String nitEmisor = "";
    private static String razonSocialEmisor = "";
    private static String municipio = "";
    private static String telefono = "";
    private static String codigoSucursal = "";
    private static String direccion = "";

    private LocalDateTime date;
    private int numeroFactura;
    private int cuf;

    private Client client;

    private Sale sale;
    public Bill(int numeroFactura, int cud, Client client, Sale sale){
        this.numeroFactura = numeroFactura;
        this.cuf = cuf;
        this.date = LocalDateTime.now();
        this.client = client;
        this.sale = sale;
    }

    public void createXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.newDocument();

        Element rootElement = document.createElement("facturaComputarizadaCompraVenta");
        document.appendChild(rootElement);

        Element cabecera = document.createElement("cabecera");

        Element nitEmisor = document.createElement("nitEmisor");
        Text nit = document.createTextNode(String.valueOf(client.getNIT()));
        nitEmisor.appendChild(nit);
        cabecera.appendChild(nitEmisor);

        /////Llenar Datos

        rootElement.appendChild(cabecera);

        Element detalle = document.createElement("detalle");

        for(AddedProduct product : sale.getCart()){
            Element producto = document.createElement("producto");

            Element codigoProducto = document.createElement("codigoProducto");
            Text codigoProductoText = document.createTextNode(String.valueOf(product.getCode()));
            codigoProducto.appendChild(codigoProductoText);
            producto.appendChild(codigoProducto);

            Element descripcion = document.createElement("descripcion");
            Text descripcionText = document.createTextNode(String.valueOf(product.getDescription()));
            descripcion.appendChild(descripcionText);
            producto.appendChild(descripcion);

            Element cantidad = document.createElement("cantidad");
            Text cantidadText = document.createTextNode(String.valueOf(product.getCant()));
            cantidad.appendChild(cantidadText);
            producto.appendChild(cantidad);

            Element unidadMedida = document.createElement("unidadMedida");
            Text unidadMedidaText = document.createTextNode(String.valueOf(product.getCode())); /// Poner unidar de medida en producto
            unidadMedida.appendChild(unidadMedidaText);
            producto.appendChild(unidadMedida);

            Element precioUnitario = document.createElement("precioUnitario");
            Text precioUnitarioText = document.createTextNode(String.valueOf(product.getPrice()));
            precioUnitario.appendChild(precioUnitarioText);
            producto.appendChild(precioUnitario);


            //// Llenar datos

            detalle.appendChild(producto);
        }

        rootElement.appendChild(detalle);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indent the XML for readability

        // Specify the file path to save the XML document
        String filePath = "Factura-" + String.valueOf(this.numeroFactura) + ".xml";
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(filePath);
        transformer.transform(source, result);

    }

    public void createPDF(){

    }

    public void createHTML(){

    }
}
