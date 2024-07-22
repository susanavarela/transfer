package com.transferFile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.apache.commons.csv.CSVFormat;

@Service
@Slf4j
@RequiredArgsConstructor
public class service {

    public static final String FILE_NAME = "name";
    public static final String EXTENSION = ".csv";
    public static final String ZONE = "America/Argentina/Buenos_Aires";

    public static final ZoneId ZONE_ID = ZoneId.of(ZONE);
    private String fileName;
    public void report(){
        LocalDateTime startJob = LocalDateTime.now(ZONE_ID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalTime timeLimit = LocalTime.of(11,20);


        log.info("Starting report - Date: {}", startJob);

        if (startJob.toLocalTime().isAfter(timeLimit)){
            fileName = FILE_NAME + startJob.plusDays(1).format(formatter) +
                     EXTENSION;
        }else{
            fileName = FILE_NAME + startJob.format(formatter) + EXTENSION;
        }

        try {
            FTPClient ftpClient = null;
            CSVFormat csvFormat = null;

            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

            String[] files = ftpClient.listNames(/*ruta*/"c:");

            if(files != null && files.length > 0){
                csvFormat = CSVFormat.DEFAULT.builder().build();
            }else{
                String[] headers = {"NAME","PASSWORD"};
                csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).build();
            }

            try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ftpClient.appendFileStream(/*ruta*/"c:"),StandardCharsets.UTF_8);
            CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter, csvFormat)){

                csvPrinter.printRecord("hola");

                csvPrinter.flush();
            }

            ftpClient.disconnect();
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }






}
