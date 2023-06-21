package ElectronicInvoice;

import application.AddedProduct;
import application.Client;
import application.Sale;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Invoice {
    private static int invoiceNumber = 0;
    private static String CUF;
    private static String CUFD;
    private static LocalDateTime issueDate = LocalDateTime.now();
    private Client client;
    private Sale sale;

    private static final String numbersLettersCUF = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbersLettersCUFD = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ+=abcdefghijklmnopqrstuvwxyz";
    private static SecureRandom secureRandom = new SecureRandom();

    public Invoice(Client client, Sale sale) {
        this.client = client;
        this.sale = sale;
    }

    public void createXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        DOMImplementation domImplementation = documentBuilder.getDOMImplementation();

        Document document = domImplementation.createDocument(null, "FacturaElectronicaFerreteriaDIMACO", null);
        document.setXmlVersion("1.0");
        document.setXmlStandalone(true);

        document.getDocumentElement().setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        document.getDocumentElement().setAttribute("xsi:noNamespaceSchemaLocation" ,"FacturaElectronicaFerreteriaDIMACO.xsd");

        Element cabecera = document.createElement("cabecera");

        Element nitEmisor = document.createElement("nitEmisor");
        Text nitEmisorText = document.createTextNode(String.valueOf(HardwareStore.getNIT()));
        nitEmisor.appendChild(nitEmisorText);
        cabecera.appendChild(nitEmisor);

        Element razonSocialEmisor = document.createElement("razonSocialEmisor");
        Text razonSocialEmisorText = document.createTextNode(HardwareStore.getName());
        razonSocialEmisor.appendChild(razonSocialEmisorText);
        cabecera.appendChild(razonSocialEmisor);

        Element municipio = document.createElement("municipio");
        Text municipioText = document.createTextNode(HardwareStore.getMunicipality());
        municipio.appendChild(municipioText);
        cabecera.appendChild(municipio);

        Element telefono = document.createElement("telefono");
        Text telefonoText = document.createTextNode(String.valueOf(HardwareStore.getTelephone()));
        telefono.appendChild(telefonoText);
        cabecera.appendChild(telefono);

        Element numeroFactura = document.createElement("numeroFactura");
        Text numeroFacturaText = document.createTextNode(String.valueOf(incrementInvoiceNumber()));
        numeroFactura.appendChild(numeroFacturaText);
        cabecera.appendChild(numeroFactura);

        Element cuf = document.createElement("cuf");
        Text cufText = document.createTextNode(generateCUF());
        cuf.appendChild(cufText);
        cabecera.appendChild(cuf);

        Element cufd = document.createElement("cufd");
        Text cufdText = document.createTextNode(generateCUFD());
        cufd.appendChild(cufdText);
        cabecera.appendChild(cufd);

        Element codigoSucursal = document.createElement("codigoSucursal");
        Text codigoSucursalText = document.createTextNode(String.valueOf(HardwareStore.getCodeBranch()));
        codigoSucursal.appendChild(codigoSucursalText);
        cabecera.appendChild(codigoSucursal);

        Element direccion = document.createElement("direccion");
        Text direccionText = document.createTextNode(HardwareStore.getAddress());
        direccion.appendChild(direccionText);
        cabecera.appendChild(razonSocialEmisor);

        Element codigoPuntoVenta = document.createElement("codigoPuntoVenta");
        codigoPuntoVenta.setAttribute("xsi:nil", "true");
        cabecera.appendChild(codigoPuntoVenta);

        Element fechaEmision = document.createElement("fechaEmision");
        Text fechaEmisionText = document.createTextNode(String.valueOf(issueDate));
        fechaEmision.appendChild(fechaEmisionText);
        cabecera.appendChild(fechaEmision);

        Element nombreRazonSocial = document.createElement("nombreRazonSocial");
        Text nombreRazonSocialText = document.createTextNode(client.getName());
        nombreRazonSocial.appendChild(nombreRazonSocialText);
        cabecera.appendChild(nombreRazonSocial);

        Element codigoTipoDocumentoIdentidad = document.createElement("codigoTipoDocumentoIdentidad");
        Text codigoTipoDocumentoIdentidadText = document.createTextNode(String.valueOf(client.getDocumentTypeCode()));
        codigoTipoDocumentoIdentidad.appendChild(codigoTipoDocumentoIdentidadText);
        cabecera.appendChild(codigoTipoDocumentoIdentidad);

        Element numeroDocumento = document.createElement("numeroDocumento");
        Text numeroDocumentoText = document.createTextNode(String.valueOf(client.getNIT()));
        numeroDocumento.appendChild(numeroDocumentoText);
        cabecera.appendChild(numeroDocumento);

        Element complemento = document.createElement("complemento");
        complemento.setAttribute("xsi:nil", "true");
        cabecera.appendChild(complemento);

        Element codigoCliente = document.createElement("codigoCliente");
        Text codigoClienteText = document.createTextNode(String.valueOf(1));
        codigoCliente.appendChild(codigoClienteText);
        cabecera.appendChild(codigoCliente);

        Element codigoMetodoPago = document.createElement("codigoMetodoPago");
        Text codigoMetodoPagoText = document.createTextNode(String.valueOf(1));
        codigoMetodoPago.appendChild(codigoMetodoPagoText);
        cabecera.appendChild(codigoMetodoPago);

        Element numeroTarjeta = document.createElement("numeroTarjeta");
        numeroTarjeta.setAttribute("xsi:nil", "true");
        cabecera.appendChild(numeroTarjeta);

        Element montoTotal = document.createElement("montoTotal");
        Text montoTotalText = document.createTextNode("");
        montoTotal.appendChild(montoTotalText);
        cabecera.appendChild(montoTotal);

        Element montoTotalSujetoIva = document.createElement("montoTotalSujetoIva");
        Text montoTotalSujetoIvaText = document.createTextNode("");
        montoTotalSujetoIva.appendChild(montoTotalSujetoIvaText);
        cabecera.appendChild(montoTotalSujetoIva);

        Element codigoMoneda = document.createElement("codigoMoneda");
        Text codigoMonedaText = document.createTextNode(String.valueOf(1));
        codigoMoneda.appendChild(codigoMonedaText);
        cabecera.appendChild(codigoMoneda);

        Element tipoCambio = document.createElement("tipoCambio");
        Text tipoCambioText = document.createTextNode(String.valueOf(1));
        tipoCambio.appendChild(tipoCambioText);
        cabecera.appendChild(tipoCambio);

        Element montoTotalMoneda = document.createElement("montoTotalMoneda");
        Text montoTotalMonedaText = document.createTextNode("");
        montoTotalMoneda.appendChild(montoTotalMonedaText);
        cabecera.appendChild(montoTotalMoneda);

        Element montoGiftCard = document.createElement("montoGiftCard");
        montoGiftCard.setAttribute("xsi:nil", "true");
        cabecera.appendChild(montoGiftCard);

        Element descuentoAdicional = document.createElement("descuentoAdicional");
        descuentoAdicional.setAttribute("xsi:nil", "true");
        cabecera.appendChild(descuentoAdicional);

        Element codigoExcepcion = document.createElement("codigoExcepcion");
        codigoExcepcion.setAttribute("xsi:nil", "true");
        cabecera.appendChild(codigoExcepcion);

        Element cafc = document.createElement("cafc");
        cafc.setAttribute("xsi:nil", "true");
        cabecera.appendChild(cafc);

        Element leyenda = document.createElement("leyenda");
        Text leyendaText = document.createTextNode("Ley N° 453: Tienes derecho a recibir informacion sobre las caracteristicas y contenidos de los servicios que utilices. \"Este documento es la representacion grafica de un Documento Fiscal Digital emitido en una Modalidad de Facturación Electronica\"");
        leyenda.appendChild(leyendaText);
        cabecera.appendChild(leyenda);

        Element usuario = document.createElement("usuario");
        Text usuarioText = document.createTextNode("");
        usuario.appendChild(usuarioText);
        cabecera.appendChild(usuario);

        Element codigoDocumentoSector = document.createElement("razonSocialEmisor");
        Text codigoDocumentoSectorText = document.createTextNode(String.valueOf(1));
        codigoDocumentoSector.appendChild(codigoDocumentoSectorText);
        cabecera.appendChild(codigoDocumentoSector);

        document.getDocumentElement().appendChild(cabecera);

        for (AddedProduct product : sale.getCart()) {
            Element detalle = document.createElement("detalle");

            Element actividadEconomica = document.createElement("actividadEconomica");
            Text actividadEconomicaText = document.createTextNode("");
            actividadEconomica.appendChild(actividadEconomicaText);
            detalle.appendChild(actividadEconomica);

            Element codigoProductoSin = document.createElement("codigoProductoSin");
            Text codigoProductoSinText = document.createTextNode("");
            codigoProductoSin.appendChild(codigoProductoSinText);
            detalle.appendChild(codigoProductoSin);

            Element codigoProducto = document.createElement("codigoProducto");
            Text codigoProductoText = document.createTextNode(String.valueOf(product.getCode()));
            codigoProducto.appendChild(codigoProductoText);
            detalle.appendChild(codigoProducto);

            Element descripcion = document.createElement("descripcion");
            Text descripcionText = document.createTextNode(product.getDescription());
            descripcion.appendChild(descripcionText);
            detalle.appendChild(descripcion);

            Element cantidad = document.createElement("cantidad");
            Text cantidadText = document.createTextNode(String.valueOf(product.getCant()));
            cantidad.appendChild(cantidadText);
            detalle.appendChild(cantidad);

            Element unidadMedida = document.createElement("unidadMedida");
            Text unidadMedidaText = document.createTextNode(""); // Poner unidad de medida en producto
            unidadMedida.appendChild(unidadMedidaText);
            detalle.appendChild(unidadMedida);

            Element precioUnitario = document.createElement("precioUnitario");
            Text precioUnitarioText = document.createTextNode(String.valueOf(product.getPrice()));
            precioUnitario.appendChild(precioUnitarioText);
            detalle.appendChild(precioUnitario);

            Element montoDescuento = document.createElement("montoDescuento");
            montoDescuento.setAttribute("xsi:nil", "true");
            detalle.appendChild(montoDescuento);

            Element subTotal = document.createElement("subTotal");
            Text subTotalText = document.createTextNode("");
            subTotal.appendChild(subTotalText);
            detalle.appendChild(subTotal);

            Element numeroSerie = document.createElement("numeroSerie");
            numeroSerie.setAttribute("xsi:nil", "true");
            detalle.appendChild(numeroSerie);

            Element numeroImei = document.createElement("numeroImei");
            numeroImei.setAttribute("xsi:nil", "true");
            detalle.appendChild(numeroImei);

            document.getDocumentElement().appendChild(detalle);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("FacturaElectronicaFerreteriaDIMACO.xml"));

        transformer.transform(source, result);
    }

    public int incrementInvoiceNumber() {
        invoiceNumber++;
        return invoiceNumber;
    }

    public String generateCUF() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            stringBuilder.append(numbersLettersCUF.charAt(secureRandom.nextInt(numbersLettersCUF.length())));
        }
        return String.valueOf(stringBuilder);
    }

    public String generateCUFD() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 70; i++) {
            stringBuilder.append(numbersLettersCUFD.charAt(secureRandom.nextInt(numbersLettersCUFD.length())));
        }
        return String.valueOf(stringBuilder);
    }
}
