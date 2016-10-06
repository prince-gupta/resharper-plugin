package org.sonar.plugins.resharperplugin.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.SonarException;
import org.sonar.api.utils.StaxParser;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FilenameFilter;

public class ReportParser {

	private static final String REPORT_PREFIX = "rs-";
	private static final String REPORT_EXTENSION = ".xml";
	Logger log = LoggerFactory.getLogger(this.getClass());

	public ReportRepo parseReports(File reportDirectory, ActiveRules activeRules, SensorContext sensorContext, Project project, ResourcePerspectives perspectives) {
		log.info("Parsing Started. . .");
		if (reportDirectory != null && reportDirectory.isDirectory()) {
			log.info("Macthing for files configured with REPORT_PREFIX : " + REPORT_PREFIX + " and REPORT_EXTENSION : " + REPORT_EXTENSION);
			File[] resultFiles = matchFiles(reportDirectory, REPORT_PREFIX, REPORT_EXTENSION);
			log.info(resultFiles.length + " Files have been filtered to analyze.");
			if (resultFiles.length > 0) {
				ReportRepo reportRepo = new ReportRepo();
				parseResults(resultFiles, reportRepo, activeRules, sensorContext, project, perspectives);
				log.info("Returning Index.");
				return reportRepo;
			} else {
			}
		}
		return null;
	}

	private void parseResults(File[] reports, ReportRepo reportRepo, ActiveRules activeRules, SensorContext sensorContext, Project project, ResourcePerspectives perspectives) {
		SurefireStaxHandler staxParser = new SurefireStaxHandler(reportRepo, activeRules, sensorContext, project, perspectives);
		StaxParser parser = new StaxParser(staxParser, false);
		for (File report : reports) {
			try {
				log.info("Parsing " + report.getAbsolutePath());
				parser.parse(report);
			} catch (XMLStreamException e) {
				throw new SonarException("Fail to parse the integration test report: " + report, e);
			}
		}
	}

	private File[] matchFiles(File directory, final String prefix, final String extension) {
		return directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(prefix) && name.endsWith(extension);
			}
		});
	}
}