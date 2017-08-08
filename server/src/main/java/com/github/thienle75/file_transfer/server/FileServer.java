package com.github.thienle75.file_transfer.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileServer {

	@RequestMapping(method = RequestMethod.GET, path = "/{filename}", produces = "application/octet-stream")
	public ResponseEntity<InputStreamResource> download(
			@RequestHeader(name = "Range", defaultValue = "bytes=0-") String rangeSpec,
			@PathVariable("filename") String filename) throws IOException {

		/* Open the file and skip to an offset */
		final File baseFileDir = new File("D:\\Downloads");
		FileInputStream fis;
		File file = new File(baseFileDir, filename);
		fis = new FileInputStream(file);
		String[] range = rangeSpec.trim().split("[=\\- ]");
		int offset = 0;
		if ("bytes".equals(range[0])) {
			offset = Integer.parseInt(range[1]);
			fis.skip(offset);
		}

		System.out.println("filename=" + filename + ", range=" + rangeSpec + ", offset=" + offset);
		InputStreamResource isr = new InputStreamResource(fis);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(file.length() - offset);
		headers.set("Content-Disposition",
				"attachment; filename=" + (offset == 0 ? filename : filename + "@" + offset));
		headers.set("Accept-Ranges", "bytes");
		headers.set("Content-Range", "bytes " + offset + "-" + (file.length() - offset) + "/" + file.length());

		return new ResponseEntity<>(isr, headers, HttpStatus.OK);
	}
}
