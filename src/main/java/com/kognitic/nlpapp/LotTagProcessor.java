//package com.kognitic.nlpapp;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import javax.sql.DataSource;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@SpringBootApplication
//public class LotTagProcessor implements CommandLineRunner {
//
//    private final DataSource dataSource;
//
//    public LotTagProcessor(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
////    public static void main(String[] args) {
////        SpringApplication.run(LotTagProcessor.class, args);
////    }
//
//    @Override
//    public void run(String... args) {
//        String filePath = "C:\\Users\\Sumalatha\\nct_id's.txt";
//
//        try (Connection conn = dataSource.getConnection();
//             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//
//            String nctId;
//            while ((nctId = reader.readLine()) != null) {
//                nctId = nctId.trim();
//                if (!nctId.isEmpty()) {
//                    callInsertSingleLotTagProcedure(conn, nctId);
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println("❌ File error: " + e.getMessage());
//        } catch (SQLException e) {
//            System.err.println("❌ DB error: " + e.getMessage());
//        }
//    }
//
//    private void callInsertSingleLotTagProcedure(Connection conn, String nctId) {
//        String call = "{CALL InsertSingleLotTag(?)}";
//
//        try (CallableStatement stmt = conn.prepareCall(call)) {
//            stmt.setString(1, nctId);
//            stmt.execute();
//            System.out.println("✅ Procedure executed for: " + nctId);
//        } catch (SQLException e) {
//            System.err.println("❌ Failed for " + nctId + ": " + e.getMessage());
//        }
//    }
//}
