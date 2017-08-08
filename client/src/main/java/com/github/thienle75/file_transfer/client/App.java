package com.github.thienle75.file_transfer.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * Takes 3 arguments. <br/>
	 * 1. source of download: http://localhost:8080/dump <br/>
	 * 2. output file name: d:/downloads/dump.out <br/>
	 * 3. max bytes to transfer in bytes: 102400 <br/>
	 */
	@Override
	public void run(String... args) throws Exception {
		String source = args[0];
		String target = args[1];
		int maxTransferBytes = 0;
		if (args.length > 2) {
			maxTransferBytes = Integer.parseInt(args[2]);
		}

		File file = new File(target);
		int offset = 0;
		String range;
		if (file.isFile()) {
			offset = (int) file.length();

			range = "bytes=" + offset + "-";
		} else {
			range = "bytes=0-";
		}

		Header rangeHeader = new BasicHeader(HttpHeaders.RANGE, range);
		List<Header> headers = Arrays.asList(rangeHeader);

		HttpClient hc = HttpClients.custom().setDefaultHeaders(headers).build();
		HttpUriRequest hur = RequestBuilder.get().setUri(source).build();

		try (FileOutputStream fos = new FileOutputStream(file, true);
				InputStream is = hc.execute(hur).getEntity().getContent()) {
			byte[] buffer = new byte[128];
			int bytes = 0;
			int bytesTransfered = 0;
			while ((bytes = is.read(buffer)) > 0) {
				fos.write(buffer, 0, bytes);
				bytesTransfered += bytes;
				if (maxTransferBytes > 0 && bytesTransfered > maxTransferBytes) {
					break;
				}
			}
		}
	}
}
