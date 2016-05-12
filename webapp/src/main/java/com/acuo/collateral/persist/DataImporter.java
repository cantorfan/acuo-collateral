package com.acuo.collateral.persist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.common.util.ArgChecker;
import com.google.common.io.Resources;

public class DataImporter {

	private static final Logger LOG = LoggerFactory.getLogger(DataImporter.class);

	private final DataLoader loader;
	private static final String ENCODING = "UTF-8";
	private final String workingDirPath;
	private final String directoryTemplate;
	private Map<String, String> substitutions = new HashMap<>();

	public DataImporter(DataLoader loader, String dataDirectory, String directoryTemplate) {
		this.loader = loader;
		this.workingDirPath = Resources.getResource(dataDirectory).getPath();
		this.directoryTemplate = directoryTemplate;
		substitutions.put("%workingDirectory%", workingDirPath);
	}

	public void importFile(String fileName) {
		try {
			String filePath = String.format(directoryTemplate, workingDirPath, fileName);
			File file = FileUtils.getFile(filePath);
			String query = buildQuery(file, substitutions);
			loader.loadData(query);
		} catch (Exception e) {
			LOG.error("an error occured while importing file {}", fileName, e);
		}
	}

	private String buildQuery(File cypherFile, Map<String, String> substitutions) throws IOException {
		ArgChecker.notNull(cypherFile, "cypherFile");
		ArgChecker.isTrue(cypherFile.exists(), "File: " + cypherFile.getName() + " does not exist!");
		String query = FileUtils.readFileToString(cypherFile, ENCODING);
		query = replacePlaceHolders(query, substitutions.entrySet());
		LOG.debug("{}", query);
		return query;
	}

	private String replacePlaceHolders(final String query, Set<Map.Entry<String, String>> placeHolders) {
		return placeHolders.stream()
				.map(entry -> (Function<String, String>) data -> data.replaceAll(entry.getKey(), entry.getValue()))
				.reduce(Function.identity(), Function::andThen).apply(query);
	}
}
