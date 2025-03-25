package com.islandpacific.smartorder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BulkOrderGenerator {
    public static void main(String[] args) {
        int numberOfOrders = 99; // Change this to generate more or fewer orders
        String baseReference = "LO250300"; // Base reference prefix
        String outputDirectory = "D:\\Bulk Orders\\LowOrders";
; // Directory to store the generated XML files

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        for (int i = 1; i <= numberOfOrders; i++) {
            String referenceNumber = baseReference + String.format("%02d", i); // HO25030001, HO25030002, ...
            String xmlContent = generateOrderXML(referenceNumber);
            String fileName = outputDirectory + "/" + referenceNumber + ".xml";
            writeToFile(fileName, xmlContent);
        }
    }

    private static String generateOrderXML(String referenceNumber) {
    	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Orders>\n" +
                "  <Order>\n" +
                "    <referenceNumber>" + referenceNumber + "</referenceNumber>\n" +
                "    <OrderType>PH</OrderType>\n" +
                "    <sourceCode>AAA</sourceCode>\n" +
                "    <currencyCode>GBP</currencyCode>\n" +
                "    <customerNumber>0000000188</customerNumber>\n" +
                "    <firstName>Sujit</firstName>\n" +
                "    <lastName>Srinivasan</lastName>\n" +
                "    <addressLine1>123 High Street</addressLine1>\n" +
                "    <city />\n" +
                "    <postalCode>45405405</postalCode>\n" +
                "    <country>UK</country>\n" +
                "    <amTelephone>9746994550</amTelephone>\n" +
                "    <handlingChargeOverridden>N</handlingChargeOverridden>\n" +
                "    <handlingCharge>5</handlingCharge>\n" +
                "    <discountOverridden>N</discountOverridden>\n" +
                "    <orderLevelDiscountPercent>0</orderLevelDiscountPercent>\n" +
                "    <receivedDate>20240301</receivedDate>\n" +
                "    <emailAddress>SSRINIVASAN@ISLANDPACIFIC.COM</emailAddress>\n" +
                "    <Lines>\n" +
                "      <Line>\n" +
                "        <lineSequence>001</lineSequence>\n" +
                "        <skuNumber>836</skuNumber>\n" +
                "        <quantityInUnits>1</quantityInUnits>\n" +
                "        <unitPrice>10.00</unitPrice>\n" +
                "        <priceIncludesVatYN>N</priceIncludesVatYN>\n" +
                "        <taxAmount>.00</taxAmount>\n" +
                "        <giftYN>N</giftYN>\n" +
                "        <deliveryMethodCode>STD</deliveryMethodCode>\n" +
                "        <shipToFirstName>SUJIT</shipToFirstName>\n" +
                "        <shipToLastName>SRINIVASAN</shipToLastName>\n" +
                "        <shipToAddressLine1>STREET 1 STREET 2</shipToAddressLine1>\n" +
                "        <shipToAddressLine2></shipToAddressLine2>\n" +
                "        <city>CHENNAI</city>\n" +
                "        <state>TN</state>\n" +
                "        <postalCode>1231</postalCode>\n" +
                "        <country>GB</country>\n" +
                "        <amTelephone>9629623424</amTelephone>\n" +
                "        <customerDiscountPercent>0</customerDiscountPercent>\n" +
                "        <customerEMailAddress>ssrinivasan@islandpacific.com</customerEMailAddress>\n" +
                "        <Discounts>\n" +
                "          <Discount>\n" +
                "            <dealNumber>00101</dealNumber>\n" +
                "            <discountAmount>0.00</discountAmount>\n" +
                "            <couponNumber>0000004646</couponNumber>\n" +
                "          </Discount>\n" +
                "        </Discounts>\n" +
                "      </Line>\n" +
                "      <Line>\n" +
                "        <lineSequence>002</lineSequence>\n" +
                "        <skuNumber>828</skuNumber>\n" +
                "        <quantityInUnits>1</quantityInUnits>\n" +
                "        <unitPrice>10.00</unitPrice>\n" +
                "        <priceIncludesVatYN>N</priceIncludesVatYN>\n" +
                "        <taxAmount>.00</taxAmount>\n" +
                "        <giftYN>N</giftYN>\n" +
                "        <deliveryMethodCode>NXTDY</deliveryMethodCode>\n" +
                "        <shipToFirstName>SUJIT</shipToFirstName>\n" +
                "        <shipToLastName>SRINIVASAN</shipToLastName>\n" +
                "        <shipToAddressLine1>STREET 1 STREET 2</shipToAddressLine1>\n" +
                "        <shipToAddressLine2></shipToAddressLine2>\n" +
                "        <city>CHENNAI</city>\n" +
                "        <state>TN</state>\n" +
                "        <postalCode>1231</postalCode>\n" +
                "        <country>GB</country>\n" +
                "        <amTelephone>9629623424</amTelephone>\n" +
                "        <customerDiscountPercent>0</customerDiscountPercent>\n" +
                "        <customerEMailAddress>ssrinivasan@islandpacific.com</customerEMailAddress>\n" +
                "        <Discounts>\n" +
                "          <Discount>\n" +
                "            <dealNumber>00101</dealNumber>\n" +
                "            <discountAmount>0.00</discountAmount>\n" +
                "            <couponNumber>0000004646</couponNumber>\n" +
                "          </Discount>\n" +
                "        </Discounts>\n" +
                "      </Line>\n" +
                "    </Lines>\n" +
                "    <Payments>\n" +
                "      <Payment>\n" +
                "        <paymentSequenceNumber>1</paymentSequenceNumber>\n" +
                "        <paymentTypeCode>CA</paymentTypeCode>\n" +
                "        <reference>CA</reference>\n" +
                "        <paymentAmount>250.00</paymentAmount>\n" +
                "      </Payment>\n" +
                "    </Payments>\n" +
                "    <Messages />\n" +
                "  </Order>\n" +
                "</Orders>";
    }

    private static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("Generated: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing file " + fileName + ": " + e.getMessage());
        }
    }
}
