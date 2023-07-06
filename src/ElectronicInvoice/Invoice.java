package ElectronicInvoice;

import InventoryModel.Product;
import SalesModel.Client;
import SalesModel.Sale;
import application.FlowController.User;
import com.aspose.words.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Invoice {
    private static int invoiceNumber = 0;
    private static LocalDateTime issueDate = LocalDateTime.now();
    private Client client;
    private Sale sale;
    private User userData;
    private static final int invoiceTypeCode = 1;

    private static final String numbersLettersCUF = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbersLettersCUFD = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ+=abcdefghijklmnopqrstuvwxyz";
    private static SecureRandom secureRandom = new SecureRandom();

    public Invoice(Client client, Sale sale) throws Exception {
        this.client = client;
        this.sale = sale;
        createXML();
        generateAndDownloadPDF();
    }

    private void createXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        DOMImplementation domImplementation = documentBuilder.getDOMImplementation();

        Document XMLDocument = domImplementation.createDocument(null, "FacturaElectronicaFerreteriaDIMACO", null);
        XMLDocument.setXmlVersion("1.0");
        XMLDocument.setXmlStandalone(true);

        XMLDocument.getDocumentElement().setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        XMLDocument.getDocumentElement().setAttribute("xsi:noNamespaceSchemaLocation" ,"FacturaElectronicaFerreteriaDIMACO.xsd");

        Element headBoard = XMLDocument.createElement("cabecera");

        Element issuerNIT = XMLDocument.createElement("nitEmisor");
        Text issuerNITText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getNIT()));
        issuerNIT.appendChild(issuerNITText);
        headBoard.appendChild(issuerNIT);

        Element issuerSocialReason = XMLDocument.createElement("razonSocialEmisor");
        Text issuerSocialReasonText = XMLDocument.createTextNode(HardwareStore.getName());
        issuerSocialReason.appendChild(issuerSocialReasonText);
        headBoard.appendChild(issuerSocialReason);

        Element municipality = XMLDocument.createElement("municipio");
        Text municipalityText = XMLDocument.createTextNode(HardwareStore.getMunicipality());
        municipality.appendChild(municipalityText);
        headBoard.appendChild(municipality);

        Element telephone = XMLDocument.createElement("telefono");
        Text telephoneText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getTelephone()));
        telephone.appendChild(telephoneText);
        headBoard.appendChild(telephone);

        Element invoiceNumb = XMLDocument.createElement("numeroFactura");
        Text invoiceNumbText = XMLDocument.createTextNode(String.valueOf(incrementInvoiceNumber()));
        invoiceNumb.appendChild(invoiceNumbText);
        headBoard.appendChild(invoiceNumb);

        Element cuf = XMLDocument.createElement("cuf");
        Text cufText = XMLDocument.createTextNode(generateCUF());
        cuf.appendChild(cufText);
        headBoard.appendChild(cuf);

        Element cufd = XMLDocument.createElement("cufd");
        Text cufdText = XMLDocument.createTextNode(generateCUFD());
        cufd.appendChild(cufdText);
        headBoard.appendChild(cufd);

        Element branchCode = XMLDocument.createElement("codigoSucursal");
        Text branchCodeText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getBranchCode()));
        branchCode.appendChild(branchCodeText);
        headBoard.appendChild(branchCode);

        Element address = XMLDocument.createElement("direccion");
        Text addressText = XMLDocument.createTextNode(HardwareStore.getAddress());
        address.appendChild(addressText);
        headBoard.appendChild(address);

        Element salePointCode = XMLDocument.createElement("codigoPuntoVenta");
        Text salePointCodeText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getSalePointCode()));
        salePointCode.appendChild(salePointCodeText);
        headBoard.appendChild(salePointCode);

        Element InvIssueDate = XMLDocument.createElement("fechaEmision");
        Text InvIssueDateText = XMLDocument.createTextNode(String.valueOf(issueDate));
        InvIssueDate.appendChild(InvIssueDateText);
        headBoard.appendChild(InvIssueDate);

        Element socialReasonName = XMLDocument.createElement("nombreRazonSocial");
        Text socialReasonNameText = XMLDocument.createTextNode(client.getName());
        socialReasonName.appendChild(socialReasonNameText);
        headBoard.appendChild(socialReasonName);

        Element identityDocumentTypeCode = XMLDocument.createElement("codigoTipoDocumentoIdentidad");
        Text identityDocumentTypeCodeText = XMLDocument.createTextNode(String.valueOf(client.getDocumentTypeCode()));
        identityDocumentTypeCode.appendChild(identityDocumentTypeCodeText);
        headBoard.appendChild(identityDocumentTypeCode);

        Element documentNumber = XMLDocument.createElement("numeroDocumento");
        Text documentNumberText = XMLDocument.createTextNode(String.valueOf(client.getNIT()));
        documentNumber.appendChild(documentNumberText);
        headBoard.appendChild(documentNumber);

        Element complement = XMLDocument.createElement("complemento");
        complement.setAttribute("xsi:nil", "true");
        headBoard.appendChild(complement);

        Element clientCode = XMLDocument.createElement("codigoCliente");
        Text clientCodeText = XMLDocument.createTextNode(String.valueOf(1111)); // String.valueOf(Código del cliente registrado en la base de datos);
        clientCode.appendChild(clientCodeText);
        headBoard.appendChild(clientCode);

        Element paymentMethodCode = XMLDocument.createElement("codigoMetodoPago");
        Text paymentMethodCodeText = XMLDocument.createTextNode(String.valueOf(Sale.getPaymentMethodCode()));
        paymentMethodCode.appendChild(paymentMethodCodeText);
        headBoard.appendChild(paymentMethodCode);

        Element cardNumber = XMLDocument.createElement("numeroTarjeta");
        cardNumber.setAttribute("xsi:nil", "true");
        headBoard.appendChild(cardNumber);

        Element totalAmount = XMLDocument.createElement("montoTotal");
        Text totalAmountText = XMLDocument.createTextNode(String.valueOf(sale.getMonto()));
        totalAmount.appendChild(totalAmountText);
        headBoard.appendChild(totalAmount);

        Element ivaSubjectTotalAmount = XMLDocument.createElement("montoTotalSujetoIva");
        Text ivaSubjectTotalAmountText = XMLDocument.createTextNode(String.valueOf(sale.getMonto()));
        ivaSubjectTotalAmount.appendChild(ivaSubjectTotalAmountText);
        headBoard.appendChild(ivaSubjectTotalAmount);

        Element coinCode = XMLDocument.createElement("codigoMoneda");
        Text coinCodeText = XMLDocument.createTextNode(String.valueOf(Sale.getCoinCode()));
        coinCode.appendChild(coinCodeText);
        headBoard.appendChild(coinCode);

        Element exchangeRate = XMLDocument.createElement("tipoCambio");
        Text exchangeRateText = XMLDocument.createTextNode(String.valueOf(Sale.getExchangeRate()));
        exchangeRate.appendChild(exchangeRateText);
        headBoard.appendChild(exchangeRate);

        Element coinTotalAmount = XMLDocument.createElement("montoTotalMoneda");
        Text coinTotalAmountText = XMLDocument.createTextNode(String.valueOf(sale.getMonto()));
        coinTotalAmount.appendChild(coinTotalAmountText);
        headBoard.appendChild(coinTotalAmount);

        Element giftCardAmount = XMLDocument.createElement("montoGiftCard");
        giftCardAmount.setAttribute("xsi:nil", "true");
        headBoard.appendChild(giftCardAmount);

        Element additionalDiscount = XMLDocument.createElement("descuentoAdicional");
        additionalDiscount.setAttribute("xsi:nil", "true");
        headBoard.appendChild(additionalDiscount);

        Element exceptionCode = XMLDocument.createElement("codigoExcepcion");
        exceptionCode.setAttribute("xsi:nil", "true");
        headBoard.appendChild(exceptionCode);

        Element cafc = XMLDocument.createElement("cafc");
        cafc.setAttribute("xsi:nil", "true");
        headBoard.appendChild(cafc);

        Element caption = XMLDocument.createElement("leyenda");
        Text captionText = XMLDocument.createTextNode("Ley N° 453: Tienes derecho a recibir información sobre las características y contenidos de los servicios que utilices.");
        caption.appendChild(captionText);
        headBoard.appendChild(caption);

        String userName = userData.getNames() + " " + userData.getLastNames();
        Element user = XMLDocument.createElement("usuario");
        Text userText = XMLDocument.createTextNode(userName);
        user.appendChild(userText);
        headBoard.appendChild(user);

        Element sectorDocumentCode = XMLDocument.createElement("codigoDocumentoSector");
        Text sectorDocumentCodeText = XMLDocument.createTextNode(String.valueOf(invoiceTypeCode));
        sectorDocumentCode.appendChild(sectorDocumentCodeText);
        headBoard.appendChild(sectorDocumentCode);

        XMLDocument.getDocumentElement().appendChild(headBoard);

        for (Product.AddedProduct Adproduct : sale.getCart().collection) {
            Product product = Adproduct.getProduct();
            Element detail = XMLDocument.createElement("detalle");

            Element economicActivity = XMLDocument.createElement("actividadEconomica");
            Text economicActivityText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getEconomicActivityCode()));
            economicActivity.appendChild(economicActivityText);
            detail.appendChild(economicActivity);

            Element SINProductCode = XMLDocument.createElement("codigoProductoSin");
            Text SINProductCodeText = XMLDocument.createTextNode(String.valueOf(HardwareStore.getSINProductCode()));
            SINProductCode.appendChild(SINProductCodeText);
            detail.appendChild(SINProductCode);

            Element productCode = XMLDocument.createElement("codigoProducto");
            Text productCodeText = XMLDocument.createTextNode(String.valueOf(product.getCode()));
            productCode.appendChild(productCodeText);
            detail.appendChild(productCode);

            Element description = XMLDocument.createElement("descripcion");
            Text descriptionText = XMLDocument.createTextNode(product.getDescription());
            description.appendChild(descriptionText);
            detail.appendChild(description);

            Element quantity = XMLDocument.createElement("cantidad");
            Text quantityText = XMLDocument.createTextNode(String.valueOf(product.getQuantity()));
            quantity.appendChild(quantityText);
            detail.appendChild(quantity);

            Element measurementUnit = XMLDocument.createElement("unidadMedida");
            Text measurementUnitText = XMLDocument.createTextNode(String.valueOf(Product.getMeasurementUnitCode()));
            measurementUnit.appendChild(measurementUnitText);
            detail.appendChild(measurementUnit);

            Element unitPrice = XMLDocument.createElement("precioUnitario");
            Text unitPriceText = XMLDocument.createTextNode(String.valueOf(product.getPrice()));
            unitPrice.appendChild(unitPriceText);
            detail.appendChild(unitPrice);

            Element discountAmount = XMLDocument.createElement("montoDescuento");
            Text discountAmountText = XMLDocument.createTextNode(String.valueOf(Sale.getDiscountAmount()));
            discountAmount.appendChild(discountAmountText);
            detail.appendChild(discountAmount);

            double subTotalCalculation = (product.getQuantity() * product.getPrice()) - Sale.getDiscountAmount();
            Element subTotal = XMLDocument.createElement("subTotal");
            Text subTotalText = XMLDocument.createTextNode(String.valueOf(subTotalCalculation));
            subTotal.appendChild(subTotalText);
            detail.appendChild(subTotal);

            Element serialNumber = XMLDocument.createElement("numeroSerie");
            serialNumber.setAttribute("xsi:nil", "true");
            detail.appendChild(serialNumber);

            Element imeiNumber = XMLDocument.createElement("numeroImei");
            imeiNumber.setAttribute("xsi:nil", "true");
            detail.appendChild(imeiNumber);

            XMLDocument.getDocumentElement().appendChild(detail);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        String XMLFileName = "Factura" + invoiceNumber + ".xml";
        String XMLFilesPath = "Facturas - Archivos XML/" + XMLFileName;
        Source source = new DOMSource(XMLDocument);
        Result result = new StreamResult(new File(XMLFilesPath));

        transformer.transform(source, result);
    }

    private void generateAndDownloadPDF() throws Exception {
        String invoiceDesignFileName = "InvoiceDesign.docx";
        String invoiceDesignFilePath = "src/" + invoiceDesignFileName;
        String XMLFileName = "Factura" + invoiceNumber + ".xml";
        String XMLFilesPath = "Facturas - Archivos XML/" + XMLFileName;
        String tempWORDFileName = "tempInvoice.docx";
        String tempWORDFilePath = "src/" + tempWORDFileName;
        String PDFFileName = "Factura" + invoiceNumber + ".pdf";
        String PDFFilePath = "C:/Users/usuario/Downloads/" + PDFFileName;

        com.aspose.words.Document invoiceWordDocument = new com.aspose.words.Document(invoiceDesignFilePath);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document XMLDocument = documentBuilder.parse(new File(XMLFilesPath));
        XMLDocument.getDocumentElement().normalize();

        NodeList headBoardNodeList = XMLDocument.getElementsByTagName("cabecera");
        Node headBoardNode = headBoardNodeList.item(0);
        Element headBoardNodeElements = (Element) headBoardNode;

        int issuerNIT = Integer.parseInt(headBoardNodeElements.getElementsByTagName("nitEmisor").item(0).getTextContent());
        String issuerSocialReason = headBoardNodeElements.getElementsByTagName("razonSocialEmisor").item(0).getTextContent();
        String municipality = headBoardNodeElements.getElementsByTagName("municipio").item(0).getTextContent();
        int telephone = Integer.parseInt(headBoardNodeElements.getElementsByTagName("telefono").item(0).getTextContent());
        int invoiceNumb = Integer.parseInt(headBoardNodeElements.getElementsByTagName("numeroFactura").item(0).getTextContent());
        String address = headBoardNodeElements.getElementsByTagName("direccion").item(0).getTextContent();
        int salePointCode = Integer.parseInt(headBoardNodeElements.getElementsByTagName("codigoPuntoVenta").item(0).getTextContent());
        String socialReasonName = headBoardNodeElements.getElementsByTagName("nombreRazonSocial").item(0).getTextContent();
        int documentNumber = Integer.parseInt(headBoardNodeElements.getElementsByTagName("numeroDocumento").item(0).getTextContent());
        int clientCode = Integer.parseInt(headBoardNodeElements.getElementsByTagName("codigoCliente").item(0).getTextContent());
        double totalAmount = Double.parseDouble(headBoardNodeElements.getElementsByTagName("montoTotal").item(0).getTextContent());
        String totalAmountText = String.valueOf(totalAmount);
        String[] splitTotalAmount = totalAmountText.split("\\.");
        String cents = "CON " + splitTotalAmount[1] + "/100";
        String userName = headBoardNodeElements.getElementsByTagName("usuario").item(0).getTextContent();

        setFormFieldValue(invoiceWordDocument, "issuerNIT", String.valueOf(issuerNIT));
        setFormFieldValue(invoiceWordDocument, "issuerSocialReason", issuerSocialReason);
        setFormFieldValue(invoiceWordDocument, "municipality", municipality);
        setFormFieldValue(invoiceWordDocument, "telephone", String.valueOf(telephone));
        setFormFieldValue(invoiceWordDocument, "invoiceNumber", String.valueOf(invoiceNumb));
        setFormFieldValue(invoiceWordDocument, "address", address);
        setFormFieldValue(invoiceWordDocument, "salePointCode", String.valueOf(salePointCode));
        setFormFieldValue(invoiceWordDocument, "documentNumber", String.valueOf(documentNumber));
        setFormFieldValue(invoiceWordDocument, "socialReasonName", socialReasonName);
        setFormFieldValue(invoiceWordDocument, "clientCode", String.valueOf(clientCode));
        setFormFieldValue(invoiceWordDocument, "totalAmount", totalAmountText);
        setFormFieldValue(invoiceWordDocument, "userName", userName);
        setFormFieldValue(invoiceWordDocument, "totalAmountText", numberToText(totalAmountText) + cents);
        setFormFieldValue(invoiceWordDocument, "subTotal", totalAmountText);
        setFormFieldValue(invoiceWordDocument, "fullPayment", totalAmountText);
        setFormFieldValue(invoiceWordDocument, "taxCreditBaseAmount", totalAmountText);

        Table saleTable = invoiceWordDocument.getLastSection().getBody().getTables().get(0);
        Row tableHeader = saleTable.getFirstRow();

        NodeList detailNodeList = XMLDocument.getElementsByTagName("detalle");
        for (int i = 0; i < detailNodeList.getLength(); i++) {
            Node detailNode = detailNodeList.item(i);
            Element detailNodeElements = (Element) detailNode;

            int productCode = Integer.parseInt(detailNodeElements.getElementsByTagName("codigoProducto").item(0).getTextContent());
            String description = detailNodeElements.getElementsByTagName("descripcion").item(0).getTextContent();
            int quantity = Integer.parseInt(detailNodeElements.getElementsByTagName("cantidad").item(0).getTextContent());
            double unitPrice = Double.parseDouble(detailNodeElements.getElementsByTagName("precioUnitario").item(0).getTextContent());
            double discountAmount = Double.parseDouble(detailNodeElements.getElementsByTagName("montoDescuento").item(0).getTextContent());
            double subTotal = Double.parseDouble(detailNodeElements.getElementsByTagName("subTotal").item(0).getTextContent());

            Row dataRow = (Row) tableHeader.deepClone(true);
            for (Cell dataRowCell : dataRow) {
                dataRowCell.removeAllChildren();
                dataRowCell.ensureMinimum();
                dataRowCell.appendChild(new Paragraph(invoiceWordDocument));
                dataRowCell.getFirstParagraph().getRuns().add(new Run(invoiceWordDocument));
                dataRowCell.getFirstParagraph().getRuns().get(0).getFont().setName("Arial MT");
                dataRowCell.getFirstParagraph().getRuns().get(0).getFont().setSize(8);
                dataRowCell.getFirstParagraph().getParagraphFormat().setAlignment(1);
            }
            dataRow.getRowFormat().setHeight(31.185);

            Cell productCodeCell = dataRow.getFirstCell();
            productCodeCell.getFirstParagraph().getRuns().get(0).setText(String.valueOf(productCode));
            Cell quantityCell = dataRow.getCells().get(1);
            quantityCell.getFirstParagraph().getRuns().get(0).setText(String.valueOf(quantity));
            Cell descriptionCell = dataRow.getCells().get(2);
            descriptionCell.getFirstParagraph().getRuns().get(0).setText(description);
            Cell unitPriceCell = dataRow.getCells().get(3);
            unitPriceCell.getFirstParagraph().getRuns().get(0).setText(String.valueOf(unitPrice));
            Cell discountCell = dataRow.getCells().get(4);
            discountCell.getFirstParagraph().getRuns().get(0).setText(String.valueOf(discountAmount));
            Cell subTotalCell = dataRow.getLastCell();
            subTotalCell.getFirstParagraph().getRuns().get(0).setText(String.valueOf(subTotal));

            saleTable.getRows().insert(1, dataRow);
        }

        invoiceWordDocument.updateFields();
        invoiceWordDocument.save(tempWORDFilePath);
        com.aspose.words.Document tempInvoiceDocument = new com.aspose.words.Document(tempWORDFilePath);
        tempInvoiceDocument.save(PDFFilePath);
        File tempWORDFile = new File(tempWORDFilePath);
        tempWORDFile.delete();
    }

    private void setFormFieldValue(com.aspose.words.Document document, String bookmark, String value) throws Exception {
        FormField formField = document.getRange().getFormFields().get(bookmark);
        formField.setResult(value);
    }

    public static int getInvoiceNumber() {
        return invoiceNumber;
    }

    private int incrementInvoiceNumber() {
        invoiceNumber++;
        return invoiceNumber;
    }

    private String generateCUF() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            stringBuilder.append(numbersLettersCUF.charAt(secureRandom.nextInt(numbersLettersCUF.length())));
        }
        return String.valueOf(stringBuilder);
    }

    private String generateCUFD() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 70; i++) {
            stringBuilder.append(numbersLettersCUFD.charAt(secureRandom.nextInt(numbersLettersCUFD.length())));
        }
        return String.valueOf(stringBuilder);
    }

    private static StringBuilder numberText(int number) {
        StringBuilder result = new StringBuilder();
        int hundreds = number / 100;
        int tens  = (number % 100) / 10;
        int units = (number % 10);

        switch (hundreds) {
            case 0: break;
            case 1:
                if (tens == 0 && units == 0) {
                    result.append("CIEN ");
                    return result;
                }
                else result.append("CIENTO ");
                break;
            case 2: result.append("DOSCIENTOS "); break;
            case 3: result.append("TRESCIENTOS "); break;
            case 4: result.append("CUATROCIENTOS "); break;
            case 5: result.append("QUINIENTOS "); break;
            case 6: result.append("SEISCIENTOS "); break;
            case 7: result.append("SETECIENTOS "); break;
            case 8: result.append("OCHOCIENTOS "); break;
            case 9: result.append("NOVECIENTOS "); break;
        }

        switch (tens) {
            case 0: break;
            case 1:
                if (units == 0) { result.append("DIEZ "); return result; }
                else if (units == 1) { result.append("ONCE "); return result; }
                else if (units == 2) { result.append("DOCE "); return result; }
                else if (units == 3) { result.append("TRECE "); return result; }
                else if (units == 4) { result.append("CATORCE "); return result; }
                else if (units == 5) { result.append("QUINCE "); return result; }
                else result.append("DIECI");
                break;
            case 2:
                if (units == 0) { result.append("VEINTE "); return result; }
                else result.append("VEINTI");
                break;
            case 3: result.append("TREINTA "); break;
            case 4: result.append("CUARENTA "); break;
            case 5: result.append("CINCUENTA "); break;
            case 6: result.append("SESENTA "); break;
            case 7: result.append("SETENTA "); break;
            case 8: result.append("OCHENTA "); break;
            case 9: result.append("NOVENTA "); break;
        }

        if (tens > 2 && units > 0)
            result.append("Y ");

        switch (units) {
            case 0: break;
            case 1: result.append("UN "); break;
            case 2: result.append("DOS "); break;
            case 3: result.append("TRES "); break;
            case 4: result.append("CUATRO "); break;
            case 5: result.append("CINCO "); break;
            case 6: result.append("SEIS "); break;
            case 7: result.append("SIETE "); break;
            case 8: result.append("OCHO "); break;
            case 9: result.append("NUEVE "); break;
        }

        return result;
    }

    private static String numberToText(String stringNumber) {
        StringBuilder result = new StringBuilder();
        BigDecimal totalBigDecimal = new BigDecimal(stringNumber).setScale(2, RoundingMode.DOWN);
        long integerPart = totalBigDecimal.toBigInteger().longValue();
        int units      = (int)((integerPart % 1000));
        int thousands  = (int)((integerPart / 1000) % 1000);
        int millions   = (int)((integerPart / 1000000) % 1000);
        int billion    = (int)((integerPart / 1000000000) % 1000);

        if (integerPart == 0) {
            result.append("CERO ");
            return result.toString();
        }

        if (billion > 0)  result.append(numberText(billion).toString()).append("MIL ");
        if (millions > 0) result.append(numberText(millions).toString());

        if (billion == 0 && millions == 1)    result.append("MILLÓN ");
        else if (billion > 0 || millions > 0) result.append("MILLONES ");

        if (thousands > 0) result.append(numberText(thousands)).append("MIL ");
        if (units > 0)     result.append(numberText(units));

        return result.toString();
    }
}