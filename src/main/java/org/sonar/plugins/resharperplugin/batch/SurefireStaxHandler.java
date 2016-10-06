package org.sonar.plugins.resharperplugin.batch;

import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.codehaus.staxmate.in.SMInputCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.StaxParser;
import org.sonar.plugins.resharperplugin.objects.Issue;
import org.sonar.plugins.resharperplugin.objects.IssueType;
import org.sonar.plugins.resharperplugin.objects.IssueTypes;
import org.sonar.plugins.resharperplugin.objects.Issues;

import javax.xml.stream.XMLStreamException;

public class SurefireStaxHandler implements StaxParser.XmlStreamHandler {

    private final ReportRepo reportRepo;
    private ActiveRules rules;
    private SensorContext sensorContext;
    private Project project;
    private ResourcePerspectives perspectives;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public SurefireStaxHandler(ReportRepo reportRepo, ActiveRules rules, SensorContext sensorContext, Project project,
                               ResourcePerspectives perspectives) {
        this.reportRepo = reportRepo;
        this.rules = rules;
        this.sensorContext = sensorContext;
        this.project = project;
        this.perspectives = perspectives;
    }

    public void stream(SMHierarchicCursor rootCursor) throws XMLStreamException {
        SMInputCursor mainCursor = rootCursor.advance().childElementCursor();
        while (mainCursor.getNext() != null) {
            String nodeName = mainCursor.getQName().getLocalPart();
            if (nodeName.equals("Issues")) {
                log.info("Start Parsing Issues Bloc. . .");
                parseIssuesBloc(mainCursor);
            } else if (nodeName.equals("IssueTypes")) {
                log.info("Start Parsing IssueTypes Bloc. . .");
                parseIssueTypeBloc(mainCursor);
            }
        }
    }

    private void parseIssueTypeBloc(SMInputCursor cursor) throws XMLStreamException {
        SMInputCursor issueTypeCursor = cursor.childElementCursor("IssueType");
        IssueTypes issueTypes = new IssueTypes();
        while (issueTypeCursor.getNext() != null) {
            String id = issueTypeCursor.getAttrValue("Id");
            String catogery = issueTypeCursor.getAttrValue("Category");
            String categoryId = issueTypeCursor.getAttrValue("CategoryId");
            String subCategory = issueTypeCursor.getAttrValue("SubCategory");
            String description = issueTypeCursor.getAttrValue("Description");
            String severity = issueTypeCursor.getAttrValue("Severity");
            String wikiUrl = issueTypeCursor.getAttrValue("WikiUrl");
            IssueType issueType = new IssueType(catogery, categoryId, description, id, severity, subCategory, wikiUrl);
            issueTypes.getIssueType().add(issueType);
        }
        this.reportRepo.populateIssuesTypeMap(issueTypes);
    }

    private void parseIssuesBloc(SMInputCursor cursor)
            throws XMLStreamException {
        SMInputCursor projectsCursor = cursor.childElementCursor("Project");
        Issues issues = null;
        while (projectsCursor.getNext() != null) {
            issues = parseProjectBloc(projectsCursor, projectsCursor.getQName().getLocalPart());
        }
        this.reportRepo.populateProjectMap(issues);
    }

    private Issues parseProjectBloc(SMInputCursor projectCursor, String projectName)
            throws XMLStreamException {
        SMInputCursor issuesCursor = projectCursor.childElementCursor("Issue");
        org.sonar.plugins.resharperplugin.objects.Project project = new org.sonar.plugins.resharperplugin.objects.Project();
        Issues issues = new Issues();
        while (issuesCursor.getNext() != null) {
            String typeId = issuesCursor.getAttrValue("TypeId");
            String file = issuesCursor.getAttrValue("File");
            String offset = issuesCursor.getAttrValue("Offset");
            String line = issuesCursor.getAttrValue("Line");
            String message = issuesCursor.getAttrValue("Message");
            Issue issue = new Issue(file, line, message, offset, typeId);
            project.getIssue().add(issue);
        }
        issues.getProject().put(projectName, project);
        return (issues);
    }
}