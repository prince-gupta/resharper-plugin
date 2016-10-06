package org.sonar.plugins.resharperplugin.batch;

import org.sonar.plugins.resharperplugin.resharperPluginMetrics;
import org.sonar.plugins.resharperplugin.resharperPluginPlugin;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

public class resharperPluginSensor implements Sensor {

    private FileSystem fileSystem;
    private Settings settings;
    private final ActiveRules activeRules;
    private ResourcePerspectives perspectives;
    private final FilePredicate mainFilePredicate;
    private final FilePredicate testFilePredicate;

    private static final String JAVASCRIPT_LANGUAGE_KEY = "js";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public resharperPluginSensor(FileSystem fileSystem, Settings settings, ActiveRules rules,
                                 ResourcePerspectives perspectives) {
        this.fileSystem = fileSystem;
        this.settings = settings;
        this.activeRules = rules;
        this.perspectives = perspectives;

        this.mainFilePredicate = fileSystem.predicates().and(fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().hasLanguage(JAVASCRIPT_LANGUAGE_KEY));

        this.testFilePredicate = fileSystem.predicates().and(fileSystem.predicates().hasType(InputFile.Type.TEST),
                fileSystem.predicates().hasLanguage(JAVASCRIPT_LANGUAGE_KEY));

    }

    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

    public void analyse(Project project, SensorContext sensorContext) {

        String reportsPath = getReportsDirectoryPath();
        log.info("Path configured for report directory : " + reportsPath);
        File reportsDirectory = getFile(reportsPath);

        ReportParser parser = new ReportParser();
        ReportRepo reportRepo = parser.parseReports(reportsDirectory, activeRules, sensorContext, project, perspectives);
        if (reportRepo != null) {
            saveResults(sensorContext, reportRepo, project);
        } else {
            log.warn("Not A Valid Index. . .");
        }
    }

    private void saveResults(SensorContext context, ReportRepo reportRepo, Project sonarProject) {
        for (Map.Entry<String, org.sonar.plugins.resharperplugin.objects.Project> entry : reportRepo.getProjectMap().entrySet()) {
            String projectName = entry.getKey();
            org.sonar.plugins.resharperplugin.objects.Project project = entry.getValue();
           /* for (Map.Entry<String, Issue> innerEntry : reportRepo.convertToIssueMap(project).entrySet()) {
                String typeId = innerEntry.getKey();
                Issue issue = innerEntry.getValue();
                saveMeasures(context, context.getResource(sonarProject), issue, sonarProject.getName());
            }*/
            reportRepo.convertToIssueMap(project);
            Map<String, Integer> severityMap = reportRepo.getSeverityCountMap();
            saveMeasures(context, context.getResource(sonarProject), severityMap, sonarProject.getName());
        }

    }

    private void saveMeasures(SensorContext context, Resource resource, Map<String, Integer> severityMap, String projectInfo) {
        for (Map.Entry<String, Integer> entry : severityMap.entrySet()) {
            String severity = entry.getKey();
            switch (severity) {
                case "SUGGESTION":
                    saveMeasure(context, resource, resharperPluginMetrics.SUGGESTION, "SUGGESTION", projectInfo, entry.getValue());
                    break;
                case "WARNING":
                    saveMeasure(context, resource, resharperPluginMetrics.WARNING, "WARNING", projectInfo, entry.getValue());
                    break;
                case "DO_NOT_SHOW":
                    saveMeasure(context, resource, resharperPluginMetrics.DO_NOT_SHOW, "DO_NOT_SHOW", projectInfo, entry.getValue());
                    break;
                case "HINT":
                    saveMeasure(context, resource, resharperPluginMetrics.HINT, "HINT", projectInfo, entry.getValue());
                    break;
                case "ERROR":
                    saveMeasure(context, resource, resharperPluginMetrics.ERROR, "ERROR", projectInfo, entry.getValue());
                    break;
                default:
                    saveMeasure(context, resource, resharperPluginMetrics.SUGGESTION, "SUGGESTION", projectInfo, entry.getValue());
                    break;
            }
        }

    }

    private void saveMeasure(SensorContext context, Resource resource, Metric metric, String message,
                             String projectInfo, Integer value) {
        context.saveMeasure(resource, metric, new Double(value));
    }

    private String getTestFilename(String className) {
        String fileName = className.substring(className.indexOf('.') + 1);
        fileName = fileName.replace('.', File.separatorChar);
        fileName = fileName + ".js";
        return fileName;
    }

    private InputFile getMainFile(String fileName) {
        FilePredicate predicate = fileSystem.predicates().and(mainFilePredicate,
                fileSystem.predicates().matchesPathPattern("**" + File.separatorChar + fileName));
        Iterator<InputFile> fileIterator = fileSystem.inputFiles(predicate).iterator();
        if (fileIterator.hasNext()) {
            return fileIterator.next();
        }
        return null;
    }

    private InputFile getTestFile(String fileName) {
        FilePredicate predicate = fileSystem.predicates().and(testFilePredicate,
                fileSystem.predicates().matchesPathPattern("**" + File.separatorChar + fileName));
        Iterator<InputFile> fileIterator = fileSystem.inputFiles(predicate).iterator();
        if (fileIterator.hasNext()) {
            return fileIterator.next();
        }
        return null;
    }

    private String getReportsDirectoryPath() {

        return settings.getString(resharperPluginPlugin.REPORTS_PATH);
    }

    private File getFile(String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(fileSystem.baseDir(), path);
        }
        return file;
    }
}
