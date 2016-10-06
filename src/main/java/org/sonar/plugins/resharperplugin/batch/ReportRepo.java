package org.sonar.plugins.resharperplugin.batch;

import com.google.common.collect.Maps;
import org.sonar.plugins.resharperplugin.objects.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportRepo {


    private Map<String, IssueType> issueTypesMap;

    private Map<String, Project> projectMap;

    private Map<String, Integer> severityCountMap;

    public ReportRepo() {
        this.issueTypesMap = Maps.newHashMap();
        this.projectMap = Maps.newHashMap();
        this.severityCountMap = Maps.newHashMap();
    }

    public void populateIssuesTypeMap(IssueTypes issueTypes) {
        /*issueTypesMap = issueTypes.getIssueType()
                .stream()
                .collect(Collectors.toMap(e -> e.getId(), Function.identity()));*/
        for (IssueType issueType : issueTypes.getIssueType()) {
            this.issueTypesMap.put(issueType.getId(), issueType);
            if (this.severityCountMap.get(issueType.getSeverity()) == null) {
                this.severityCountMap.put(issueType.getSeverity(), 0);
            }
        }
    }

    public void populateProjectMap(Issues issueses) {
        this.projectMap = issueses.getProject();
    }

    public Map<String, Issue> convertToIssueMap(Project project) {
       /* return project.getIssue()
                .stream()
                .collect(Collectors.toMap(e -> e.getTypeId(), Function.identity()));*/
        Map<String, Issue> issueMap = Maps.newHashMap();
        for (Issue issue : project.getIssue()) {
            issueMap.put(issue.getTypeId(), issue);
            String severity = this.issueTypesMap.get(issue.getTypeId()).getSeverity();
            if (this.severityCountMap.get(severity) == null) {
                this.severityCountMap.put(severity, 0);
            } else {
                this.severityCountMap.put(severity, this.severityCountMap.get(severity) + 1);
            }
        }
        return issueMap;
    }

    public Map<String, Integer> getSeverityCountMap(){
        return this.severityCountMap;
    }

    public Map<String, Project> getProjectMap() {
        return this.projectMap;
    }
}