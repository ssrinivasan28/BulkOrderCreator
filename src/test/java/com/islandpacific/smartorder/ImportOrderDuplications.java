package com.islandpacific.smartorder;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ImportOrderDuplications {
    private static final Logger logger = LoggerFactory.getLogger(ImportOrderDuplications.class);
    private static Properties config = new Properties();
    private static String templateXML;

    public static void main(String[] args) {
        loadConfig();

        // Load the template XML content
        templateXML = readTemplateXML();
        if (templateXML == null) {
            logger.error("Failed to read template XML file. Exiting.");
            return;
        }

        int numberOfOrders = Integer.parseInt(config.getProperty("order.count"));
        String baseReference = config.getProperty("order.baseReference");
        String outputDirectory = config.getProperty("order.outputDirectory");

        File directory = new File(outputDirectory);
        if (!directory.exists() && !directory.mkdirs()) {
            logger.error("Failed to create output directory: {}", outputDirectory);
            return;
        }

        for (int i = 1; i <= numberOfOrders; i++) {
            String referenceNumber = baseReference + String.format("%02d", i);
            String newXmlContent = templateXML.replaceAll("<referenceNumber>.*?</referenceNumber>",
                    "<referenceNumber>" + referenceNumber + "</referenceNumber>");

            String fileName = outputDirectory + "/" + referenceNumber + ".xml";

         writeToFile(fileName, newXmlContent);
            uploadToSFTP(fileName, referenceNumber + ".xml");
        }
    }

    private static void loadConfig() {
        try (FileInputStream ip = new FileInputStream("src/main/resources/config.properties")) {
            config.load(ip);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            System.exit(1);
        }
    }

    private static String readTemplateXML() {
        try (InputStream inputStream = ImportOrderDuplications.class.getClassLoader().getResourceAsStream("template.xml")) {
            if (inputStream == null) {
                logger.error("Template XML file not found in resources.");
                return null;
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to read template XML: {}", e.getMessage());
            return null;
        }
    }

    private static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            logger.info("Generated: {}", fileName);
        } catch (IOException e) {
            logger.error("Error writing file {}: {}", fileName, e.getMessage());
        }
    }

    private static void uploadToSFTP(String localFilePath, String remoteFileName) {
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(config.getProperty("sftp.user"),
                    config.getProperty("sftp.server"),
                    Integer.parseInt(config.getProperty("sftp.port")));
            session.setPassword(config.getProperty("sftp.password"));
            session.setConfig("StrictHostKeyChecking", "no");

            logger.info("Connecting to SFTP...");
            session.connect();
            logger.info("Connected to SFTP");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            sftpChannel.cd(config.getProperty("sftp.directory"));

            try (FileInputStream inputStream = new FileInputStream(localFilePath)) {
                sftpChannel.put(inputStream, remoteFileName);
                logger.info("Uploaded: {} to SFTP", remoteFileName);
            }

            // **Delete the local file after successful upload**
            File file = new File(localFilePath);
            if (file.exists() && file.delete()) {
                logger.info("Deleted local file: {}", localFilePath);
            } else {
                logger.warn("Failed to delete local file: {}", localFilePath);
            }

        } catch (JSchException | SftpException | IOException e) {
            logger.error("SFTP upload failed: {}", e.getMessage());
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

}
