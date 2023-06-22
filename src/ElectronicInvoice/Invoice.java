package ElectronicInvoice;

import application.*;
import org.apache.pdfbox.pdmodel.PDDocument;
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
    private User userData;
    private static final int invoiceTypeCode = 1;

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

        Element headBoard = document.createElement("cabecera");

        Element issuerNIT = document.createElement("nitEmisor");
        Text issuerNITText = document.createTextNode(String.valueOf(HardwareStore.getNIT()));
        issuerNIT.appendChild(issuerNITText);
        headBoard.appendChild(issuerNIT);

        Element issuerSocialReason = document.createElement("razonSocialEmisor");
        Text issuerSocialReasonText = document.createTextNode(HardwareStore.getName());
        issuerSocialReason.appendChild(issuerSocialReasonText);
        headBoard.appendChild(issuerSocialReason);

        Element municipality = document.createElement("municipio");
        Text municipalityText = document.createTextNode(HardwareStore.getMunicipality());
        municipality.appendChild(municipalityText);
        headBoard.appendChild(municipality);

        Element telephone = document.createElement("telefono");
        Text telephoneText = document.createTextNode(String.valueOf(HardwareStore.getTelephone()));
        telephone.appendChild(telephoneText);
        headBoard.appendChild(telephone);

        Element invoiceNumb = document.createElement("numeroFactura");
        Text invoiceNumbText = document.createTextNode(String.valueOf(incrementInvoiceNumber()));
        invoiceNumb.appendChild(invoiceNumbText);
        headBoard.appendChild(invoiceNumb);

        Element cuf = document.createElement("cuf");
        Text cufText = document.createTextNode(generateCUF());
        cuf.appendChild(cufText);
        headBoard.appendChild(cuf);

        Element cufd = document.createElement("cufd");
        Text cufdText = document.createTextNode(generateCUFD());
        cufd.appendChild(cufdText);
        headBoard.appendChild(cufd);

        Element branchCode = document.createElement("codigoSucursal");
        Text branchCodeText = document.createTextNode(String.valueOf(HardwareStore.getBranchCode()));
        branchCode.appendChild(branchCodeText);
        headBoard.appendChild(branchCode);

        Element address = document.createElement("direccion");
        Text addressText = document.createTextNode(HardwareStore.getAddress());
        address.appendChild(addressText);
        headBoard.appendChild(address);

        Element salePointCode = document.createElement("codigoPuntoVenta");
        salePointCode.setAttribute("xsi:nil", "true");
        headBoard.appendChild(salePointCode);

        Element InvIssueDate = document.createElement("fechaEmision");
        Text InvIssueDateText = document.createTextNode(String.valueOf(issueDate));
        InvIssueDate.appendChild(InvIssueDateText);
        headBoard.appendChild(InvIssueDate);

        Element socialReasonName = document.createElement("nombreRazonSocial");
        Text socialReasonNameText = document.createTextNode(client.getName());
        socialReasonName.appendChild(socialReasonNameText);
        headBoard.appendChild(socialReasonName);

        Element identityDocumentTypeCode = document.createElement("codigoTipoDocumentoIdentidad");
        Text identityDocumentTypeCodeText = document.createTextNode(String.valueOf(client.getDocumentTypeCode()));
        identityDocumentTypeCode.appendChild(identityDocumentTypeCodeText);
        headBoard.appendChild(identityDocumentTypeCode);

        Element documentNumber = document.createElement("numeroDocumento");
        Text documentNumberText = document.createTextNode(String.valueOf(client.getNIT()));
        documentNumber.appendChild(documentNumberText);
        headBoard.appendChild(documentNumber);

        Element complement = document.createElement("complemento");
        complement.setAttribute("xsi:nil", "true");
        headBoard.appendChild(complement);

        Element clientCode = document.createElement("codigoCliente");
        Text clientCodeText = document.createTextNode(String.valueOf(1111)); // String.valueOf(Código del cliente registrado en la base de datos);
        clientCode.appendChild(clientCodeText);
        headBoard.appendChild(clientCode);

        Element paymentMethodCode = document.createElement("codigoMetodoPago");
        Text paymentMethodCodeText = document.createTextNode(String.valueOf(Sale.getPaymentMethodCode()));
        paymentMethodCode.appendChild(paymentMethodCodeText);
        headBoard.appendChild(paymentMethodCode);

        Element cardNumber = document.createElement("numeroTarjeta");
        cardNumber.setAttribute("xsi:nil", "true");
        headBoard.appendChild(cardNumber);

        Element totalAmount = document.createElement("montoTotal");
        Text totalAmountText = document.createTextNode(String.valueOf(sale.getMonto()));
        totalAmount.appendChild(totalAmountText);
        headBoard.appendChild(totalAmount);

        Element ivaSubjectTotalAmount = document.createElement("montoTotalSujetoIva");
        Text ivaSubjectTotalAmountText = document.createTextNode(String.valueOf(sale.getMonto()));
        ivaSubjectTotalAmount.appendChild(ivaSubjectTotalAmountText);
        headBoard.appendChild(ivaSubjectTotalAmount);

        Element coinCode = document.createElement("codigoMoneda");
        Text coinCodeText = document.createTextNode(String.valueOf(Sale.getCoinCode()));
        coinCode.appendChild(coinCodeText);
        headBoard.appendChild(coinCode);

        Element exchangeRate = document.createElement("tipoCambio");
        Text exchangeRateText = document.createTextNode(String.valueOf(Sale.getExchangeRate()));
        exchangeRate.appendChild(exchangeRateText);
        headBoard.appendChild(exchangeRate);

        Element coinTotalAmount = document.createElement("montoTotalMoneda");
        Text coinTotalAmountText = document.createTextNode(String.valueOf(sale.getMonto()));
        coinTotalAmount.appendChild(coinTotalAmountText);
        headBoard.appendChild(coinTotalAmount);

        Element giftCardAmount = document.createElement("montoGiftCard");
        giftCardAmount.setAttribute("xsi:nil", "true");
        headBoard.appendChild(giftCardAmount);

        Element additionalDiscount = document.createElement("descuentoAdicional");
        additionalDiscount.setAttribute("xsi:nil", "true");
        headBoard.appendChild(additionalDiscount);

        Element exceptionCode = document.createElement("codigoExcepcion");
        exceptionCode.setAttribute("xsi:nil", "true");
        headBoard.appendChild(exceptionCode);

        Element cafc = document.createElement("cafc");
        cafc.setAttribute("xsi:nil", "true");
        headBoard.appendChild(cafc);

        Element caption = document.createElement("leyenda");
        Text captionText = document.createTextNode("\"ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS, EL USO ILICITO SERA SANCIONADO PENALMENTE DE ACUERDO A LEY\" Ley N° 453: Tienes derecho a recibir informacion sobre las caracteristicas y contenidos de los servicios que utilices. \"Este documento es la Representacion Grafica de un Documento Fiscal Digital emitido en una Modalidad de Facturación Electronica\"");
        caption.appendChild(captionText);
        headBoard.appendChild(caption);

        String userName = userData.getNames() + " " + userData.getLastNames();
        Element user = document.createElement("usuario");
        Text userText = document.createTextNode(userName);
        user.appendChild(userText);
        headBoard.appendChild(user);

        Element sectorDocumentCode = document.createElement("codigoDocumentoSector");
        Text sectorDocumentCodeText = document.createTextNode(String.valueOf(invoiceTypeCode));
        sectorDocumentCode.appendChild(sectorDocumentCodeText);
        headBoard.appendChild(sectorDocumentCode);

        document.getDocumentElement().appendChild(headBoard);

        for (AddedProduct product : sale.getCart()) {
            Element detail = document.createElement("detalle");

            Element economicActivity = document.createElement("actividadEconomica");
            Text economicActivityText = document.createTextNode(String.valueOf(HardwareStore.getEconomicActivityCode()));
            economicActivity.appendChild(economicActivityText);
            detail.appendChild(economicActivity);

            Element SINProductCode = document.createElement("codigoProductoSin");
            Text SINProductCodeText = document.createTextNode(String.valueOf(HardwareStore.getSINProductCode()));
            SINProductCode.appendChild(SINProductCodeText);
            detail.appendChild(SINProductCode);

            Element productCode = document.createElement("codigoProducto");
            Text productCodeText = document.createTextNode(String.valueOf(product.getCode()));
            productCode.appendChild(productCodeText);
            detail.appendChild(productCode);

            Element description = document.createElement("descripcion");
            Text descriptionText = document.createTextNode(product.getDescription());
            description.appendChild(descriptionText);
            detail.appendChild(description);

            Element quantity = document.createElement("cantidad");
            Text quantityText = document.createTextNode(String.valueOf(product.getCant()));
            quantity.appendChild(quantityText);
            detail.appendChild(quantity);

            Element measurementUnit = document.createElement("unidadMedida");
            Text measurementUnitText = document.createTextNode(String.valueOf(Product.getMeasurementUnitCode()));
            measurementUnit.appendChild(measurementUnitText);
            detail.appendChild(measurementUnit);

            Element unitPrice = document.createElement("precioUnitario");
            Text unitPriceText = document.createTextNode(String.valueOf(product.getPrice()));
            unitPrice.appendChild(unitPriceText);
            detail.appendChild(unitPrice);

            Element discountAmount = document.createElement("montoDescuento");
            discountAmount.setAttribute("xsi:nil", "true");
            detail.appendChild(discountAmount);

            float subTotalCalculation = (product.getCant() * product.getPrice()) - 0;
            Element subTotal = document.createElement("subTotal");
            Text subTotalText = document.createTextNode(String.valueOf(subTotalCalculation));
            subTotal.appendChild(subTotalText);
            detail.appendChild(subTotal);

            Element serialNumber = document.createElement("numeroSerie");
            serialNumber.setAttribute("xsi:nil", "true");
            detail.appendChild(serialNumber);

            Element imeiNumber = document.createElement("numeroImei");
            imeiNumber.setAttribute("xsi:nil", "true");
            detail.appendChild(imeiNumber);

            document.getDocumentElement().appendChild(detail);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Source source = new DOMSource(document);
        Result result = new StreamResult(new File("FacturaElectronicaFerreteriaDIMACO.xml"));

        transformer.transform(source, result);
    }

    public void createPDF() {
        PDDocument pdDocument = new PDDocument();
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
