package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class FinalYearOptionsRootPane extends BorderPane {

	private FinalYearOptionsMenuBar fypMenuBar;
	private CreateStudentProfilePane studentProfile;
	private ModulePanes selectModules,reservedModules;
	private OverviewPane overviewSelection;
	private TabPane tabPaneLayout;
	
	public FinalYearOptionsRootPane() {
		//create tab pane and disable tabs from being closed
		tabPaneLayout = new TabPane();
		tabPaneLayout.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//create menu bar
		fypMenuBar = new FinalYearOptionsMenuBar();

		//create panes
		studentProfile = new CreateStudentProfilePane();
		selectModules=new ModulePanes();
		reservedModules=new ModulePanes();
		overviewSelection=new OverviewPane();
		
		//add tabs to tab pane
		tabPaneLayout.getTabs().addAll(new Tab("Create Profile",studentProfile),new Tab("Select Modules",selectModules),new Tab("Reserved Modules",reservedModules),new Tab("Overview Selection",overviewSelection));
		
		//add menu bar and tab pane to this root pane
		this.setTop(fypMenuBar);
		this.setCenter(tabPaneLayout);

		//tabPaneLayout.getTabs().get(1).setDisable(true);
		//tabPaneLayout.getTabs().get(3).getProperties().addListener(null);
		
	}

	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return studentProfile;
	}
	
	public FinalYearOptionsMenuBar getModuleSelectionToolMenuBar() {
		return fypMenuBar;
	}

	public ModulePanes getSelectModules(){
		return selectModules;
	}

	public ModulePanes getReservedModules(){
		return reservedModules;
	}

	public OverviewPane getOverviewPane(){
		return overviewSelection;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tabPaneLayout.getSelectionModel().select(index);
	}
}