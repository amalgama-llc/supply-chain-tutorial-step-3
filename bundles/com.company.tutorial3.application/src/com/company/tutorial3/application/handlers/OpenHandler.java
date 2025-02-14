package com.company.tutorial3.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import com.company.tutorial3.application.scenario.ScenarioFileFormat;
import com.company.tutorial3.application.states.AppState;
import com.company.tutorial3.common.localization.Messages;
import com.company.tutorial3.common.states.AppData;
import com.amalgamasimulation.desktop.ui.dialogs.DialogUtils;

public class OpenHandler {

	@Inject
	@Translation
	private Messages messages;

	@Inject
	private AppData appData;

	@Inject
	private AppState appState;

	@CanExecute
	private boolean canExecute() {
		return appState.isEditor();
	}

	@Execute
	public void execute(Shell shell,
						IEventBroker eventBroker, 
						MApplication app, EPartService partService, EModelService modelService, MWindow mainWindow) {
		// ask if user wants the (changed) scenario to be saved
		if (appState.ensureCurrentScenarioIsSaved(	shell,
											appData)) {
			DialogUtils.showOpenFileDialog(	shell,
											filePath -> appState.loadScenario(	filePath, true),
											ScenarioFileFormat.EXCEL.filePattern);
		}
	}
}

