package org.antop.billiardslove;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BilliardsLoveApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BilliardsLoveApplication.class, args);
    }

    private final DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SHOW TABLES");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                log.debug("initialized table = {}", rs.getString(1));
            }
        }
    }
}
