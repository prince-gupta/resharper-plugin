package org.sonar.plugins.resharperplugin.ui;

import org.sonar.api.web.*;

@UserRole(UserRole.USER)
@Description("Widget that show information about Static Code Analysis from Resharper Exported XML.")
public class resharperPluginWidget extends AbstractRubyTemplate implements RubyRailsWidget {
    @Override
    public String getId() {
        return "resharper_analysis_widget";
    }

    @Override
    public String getTitle() {
        return "Resharper Analysis Widget";
    }

    @Override
    protected String getTemplatePath() {
        return "/widgets/resharper_analysis_widget.html.erb";
    }
}