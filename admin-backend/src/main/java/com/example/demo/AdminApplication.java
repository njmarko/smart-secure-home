package com.example.demo;

import com.example.demo.model.CertificateData;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AdminApplication {

	private final CertificatesService certificatesService;
	private final CertificateDataRepository certificateDataRepository;

	@Autowired
	public AdminApplication(CertificatesService certificatesService, CertificateDataRepository certificateDataRepository) {
		this.certificatesService = certificatesService;
		this.certificateDataRepository = certificateDataRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public ApplicationRunner test() {

		return args -> {
			prepareTestMapping();
//			String csrPEM = "-----BEGIN CERTIFICATE REQUEST-----\n"
//					+ "MIICxDCCAawCAQAwfzELMAkGA1UEBhMCVVMxETAPBgNVBAgMCElsbGlub2lzMRAw\n"
//					+ "DgYDVQQHDAdDaGljYWdvMQ4wDAYDVQQKDAVDb2RhbDELMAkGA1UECwwCTkExDjAM\n"
//					+ "BgNVBAMMBUNvZGFsMR4wHAYJKoZIhvcNAQkBFg9rYmF4aUBjb2RhbC5jb20wggEi\n"
//					+ "MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDSrEF27VvbGi5x7LnPk4hRigAW\n"
//					+ "1feGeKOmRpHd4j/kUcJZLh59NHJHg5FMF7u9YdZgnMdULawFVezJMLSJYJcCAdRR\n"
//					+ "hSN+skrQlB6f5wgdkbl6ZfNaMZn5NO1Ve76JppP4gl0rXHs2UkRJeb8lguOpJv9c\n"
//					+ "tw+Sn6B13j8jF/m/OhIYI8fWhpBYvDXukgADTloCjOIsAvRonkIpWS4d014deKEe\n"
//					+ "5rhYX67m3H7GtZ/KVtBKhg44ntvuT2fR/wB1FlDws+0gp4edlkDlDml1HXsf4FeC\n"
//					+ "ogijo6+C9ewC2anpqp9o0CSXM6BT2I0h41PcQPZ4EtAc4ctKSlzTwaH0H9MbAgMB\n"
//					+ "AAGgADANBgkqhkiG9w0BAQsFAAOCAQEAqfQbrxc6AtjymI3TjN2upSFJS57FqPSe\n"
//					+ "h1YqvtC8pThm7MeufQmK9Zd+Lk2qnW1RyBxpvWe647bv5HiQaOkGZH+oYNxs1XvM\n"
//					+ "y5huq+uFPT5StbxsAC9YPtvD28bTH7iXR1b/02AK2rEYT8a9/tCBCcTfaxMh5+fr\n"
//					+ "maJtj+YPHisjxKW55cqGbotI19cuwRogJBf+ZVE/4hJ5w/xzvfdKjNxTcNr1EyBE\n"
//					+ "8ueJil2Utd1EnVrWbmHQqnlAznLzC5CKCr1WfmnrDw0GjGg1U6YpjKBTc4MDBQ0T\n"
//					+ "56ZL2yaton18kgeoWQVgcbK4MXp1kySvdWq0Bc3pmeWSM9lr/ZNwNQ==\n"
//					+ "-----END CERTIFICATE REQUEST-----\n";
//
//			InputStream stream = new ByteArrayInputStream(csrPEM.getBytes(StandardCharsets.UTF_8));
//
//			certificatesService.readCertificateSigningRequest(stream);

			certificatesService.showKeyStoreContent();
		};
	}

	public void prepareTestMapping() {
		var e1 = new CertificateData("pera", BigInteger.valueOf(1), false);
		var e2 = new CertificateData("marko", BigInteger.valueOf(2), false);
		var e3 = new CertificateData("steva", BigInteger.valueOf(3), false);
		certificateDataRepository.saveAllAndFlush(List.of(e1, e2, e3));
	}
}
