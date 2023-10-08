package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Course;
import model.RunPlan;
import model.Module;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;
import view.Message;
import view.ModulePanes;
import view.OverviewPane;

import view.CreateStudentProfilePane;
import view.FinalYearOptionsMenuBar;

/**
 * FinalYearOptionsController
 * Handles all of the data travelling between the different views and the model
 * All handlers for the different views are created within this class and passed to the relevant class to be used
 * UPDATE
 * [ ] Create log file that can be added to by the program over time, stroing all the different errors that are produced by the program over time.
 */
public class FinalYearOptionsController {

	//Private Fields
	private FinalYearOptionsRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane studentProfile;
	private ModulePanes selectedModules;
	private ModulePanes reservedModules;
	private OverviewPane overviewPane;
	private FinalYearOptionsMenuBar fypMenuBar;

	/**
	 * FinalYearOptionsController
	 * @param model - model is the data layer for the program, it stores the information relevant to the program that may been to be passed to the GUI through the controller
	 * @param view - view is the GUI layer for the program, it is where all the different GUI elements are instantiated and generates the display the user will be interacting with
	 * Assigns all the global fields.
	 * Some of the global fields are pulled from other class instances to make it easier to access other class instansiations that have occured within those classes.
	 * Creates the different moudles & assignes them to courses, then passes it through to CreateStudentProfilePane to add to the addCourseDataToComboBox
	 * Runs method attachEventHandlers to assign the different event handlers for other classes to ones created within this class.
	 */
	public FinalYearOptionsController(StudentProfile model,FinalYearOptionsRootPane view) {
		this.view = view;
		this.model = model;
		
		studentProfile = view.getCreateStudentProfilePane();
		selectedModules=view.getSelectModules();
		reservedModules=view.getReservedModules();
		overviewPane=view.getOverviewPane();
		fypMenuBar = view.getModuleSelectionToolMenuBar();

		studentProfile.addCourseDataToComboBox(importCourses());

		this.attachEventHandlers();
		this.attachDisableBindings();
		this.attachChangeListeners();
	}

	/**
	 * attachEventHandlers
	 * Private method for attaching all of the handlers created in this class to their relevant views
	 * Several of the same event handler can be used for different areas, most event handlers have been designed so that they can work on a multitude of different events
	 */
	private void attachEventHandlers() {
		studentProfile.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		selectedModules.getYearLongModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(selectedModules.getYearLongModules()));
		selectedModules.getYearLongModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(selectedModules.getYearLongModules()));
		selectedModules.getTermOneModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(selectedModules.getTermOneModules()));
		selectedModules.getTermOneModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(selectedModules.getTermOneModules()));
		selectedModules.getTermTwoModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(selectedModules.getTermTwoModules()));
		selectedModules.getTermTwoModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(selectedModules.getTermTwoModules()));

		selectedModules.setResetButtonHandler(new ModulePanesResetBtnHandler(selectedModules,false));
		selectedModules.setSubmitButtonHandler(new SelectedModulesSubmitBtnHandler());

		reservedModules.getYearLongModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(reservedModules.getYearLongModules()));
		reservedModules.getYearLongModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(reservedModules.getYearLongModules()));
		reservedModules.getTermOneModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(reservedModules.getTermOneModules()));
		reservedModules.getTermTwoModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(reservedModules.getTermOneModules()));
		reservedModules.getTermTwoModules().setAddButtonHandler(new CollapsableModuleTilesAddBtnHandler(reservedModules.getTermTwoModules()));
		reservedModules.getTermTwoModules().setRemoveButtonHandler(new CollapsableModuleTilesRemoveBtnHandler(reservedModules.getTermTwoModules()));

		reservedModules.setResetButtonHandler(new ModulePanesResetBtnHandler(reservedModules,true));
		reservedModules.setSubmitButtonHandler(new ReservedModulesSubmitHandler());

		overviewPane.setSaveButtonHandler(new OverviewSelectionSaveHandler());
		
		fypMenuBar.addExitHandler(e -> System.exit(0));
		fypMenuBar.addAboutHandler(e->new Message("About","Final Year Options Chooser","Application allows students to select the modules they would like to take within their third year. They are also able to reserve modules incase the ones they have previously selected are not available.\n\nOnce all the details have been completed the data entered can be outputted as a text file available to the user.",AlertType.INFORMATION));
		fypMenuBar.addSaveHandler(new MenuBarSaveHandler());
		fypMenuBar.addLoadHandler(new MenuBarLoadHandler());
	}

	/**
	 * attachDisableBindings
	 * This private class attaches the bindings from one class to another
	 * These bindings make sure that certain actions cannot be completed without other areas having been filled in by the user before hand
	 */
	private void attachDisableBindings(){
		studentProfile.addDisableCreateProfilebtn(studentProfile.areFieldsComplete());
	}

	/**
	 * attatchChangeListeners
	 * Method attaches change listeners to the different class instances to track the changes of predetermined values/items
	 */
	private void attachChangeListeners(){
		selectedModules.getYearLongModules().setCreditsChangeListener(new CreditsChangeListener(selectedModules,RunPlan.YEAR_LONG));
		selectedModules.getTermOneModules().setCreditsChangeListener(new CreditsChangeListener(selectedModules,RunPlan.TERM_1));
		selectedModules.getTermTwoModules().setCreditsChangeListener(new CreditsChangeListener(selectedModules,RunPlan.TERM_2));

		reservedModules.getYearLongModules().setCreditsChangeListener(new CreditsChangeListener(reservedModules,RunPlan.YEAR_LONG));
		reservedModules.getTermOneModules().setCreditsChangeListener(new CreditsChangeListener(reservedModules,RunPlan.TERM_1));
		reservedModules.getTermTwoModules().setCreditsChangeListener(new CreditsChangeListener(reservedModules,RunPlan.TERM_2));
	}

	/**
	 * importCourses
	 * @return - returns array of courses 
	 * Method imports all the courses stored in the designated file location and returns them to the method call point
	 */
	private Course[] importCourses() {
		Course[] courses = new Course[2];

		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("data/savedCourses.dat"));){
			courses=(Course[])ois.readObject();
		}catch(IOException error){
			new Message("Error","Loading Courses","The courses file could not be read from.\nError: "+error.toString(),AlertType.ERROR);
		}catch(ClassNotFoundException error){
			new Message("Error","Loading Courses","The courses file could not be read from.\nError: "+error.toString(),AlertType.ERROR);
		}

		return courses;
	}

	/**
	 * updateModules - Private Class Helper Method
	 * @param view - the ModulePane that the modules should be assigned to whether selected or unselected
	 * @param isReserved - whether the passed ModulePane is the reserved ModulePane or not
	 * Method takes the passed ModulePane view and clears all the attached CollapsableModuleTiles
	 * It then goes through all the moudles attached to the course assigned to the model checking their fields
	 * As it does this it assigns modules to the correct areas within the passed ModulePane, filling in the view
	 * If the passed ModulePane is the reserved module pane then the isReserved parameter is set to true and a similar set of steps is follow but whether a module is added to the view or not is slightly altered
	 */
	private void updateModules(ModulePanes view,boolean isReserved){
		view.getYearLongModules().clearModules();
		view.getTermOneModules().clearModules();
		view.getTermTwoModules().clearModules();
		view.isAccordianDisabled(false);
		view.setExpandedPane(0);

		for(Module module:model.getStudentCourse().getAllModulesOnCourse()){
			if(((module.isMandatory())&&(!isReserved))||((!isReserved)&&(model.getAllSelectedModules().contains(module)))||((isReserved)&&(!module.isMandatory())&&(model.getAllReservedModules().contains(module)))){
				switch(module.getDelivery()){
					case YEAR_LONG: view.getYearLongModules().addSelectedModule(module); break;
					case TERM_1: view.getTermOneModules().addSelectedModule(module); break;
					case TERM_2: view.getTermTwoModules().addSelectedModule(module); break;
				}
			}else if(((isReserved)&&(module.isMandatory()))){
				switch(module.getDelivery()){
					case YEAR_LONG: view.getYearLongModules().addCredits(module.getModuleCredits()); break;
					case TERM_1: view.getTermOneModules().addCredits(module.getModuleCredits()); break;
					case TERM_2: view.getTermTwoModules().addCredits(module.getModuleCredits()); break;
				}
			}else if(((isReserved)&&(!model.getAllSelectedModules().contains(module)))||((!isReserved))){
				switch(module.getDelivery()){
					case YEAR_LONG: view.getYearLongModules().addUnselectedModule(module); break;
					case TERM_1: view.getTermOneModules().addUnselectedModule(module); break;
					case TERM_2: view.getTermTwoModules().addUnselectedModule(module); break;
				}
			}
		}
	}

	/**
	 * generateModuleDetails - Private Class Helper Method
	 * @param modules - collection of passed modules
	 * @return - string values
	 * Method goes through each of the passed modules adding their information to a string within a specific order
	 * Once all the details for each of the passed modules has been gathered the information is returned 
	 */
	private String generateModuleDetails(Set<Module> modules){
		String details="";
		for(Module module:modules){
			details+="\nModule Code: "+module.getModuleCode()+", Module Name: "+module.getModuleName()+"\nCredits: "+module.getModuleCredits()+", Mandatory Module: ";
		
			if(module.isMandatory()){
				details+="Yes";
			}else{
				details+="No";
			}

			details+=", Delivery: ";

			switch(module.getDelivery()){
				case YEAR_LONG: details+="Year Long"; break;
				case TERM_1: details+="Term 1"; break;
				case TERM_2: details+="Term 2"; break;
			}
			details+="\n";
		}
		return details;
	}
	
	/**
	 * CreateStudentProfileHandler
	 * Event handler for handling when btnCreateProfile is pressed by the user
	 * Method checks that all the relevant fields have been completed before sending the data to the model
	 * Once the data has been added to the model it clears all the boxes on the other panes 
	 * Then it adds the chosen courses modules to the given panes that they are relevant to.
	 */
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		/**
		 * handle
		 * @param  event
		 * Event handler for the create profile button within the CreateStudentProfilePane class
		 * Runs checks on the values inputted by the user to make sure that they are okay to assign to the model
		 * If the checks do not complete then an error window is displayed with how the user can correct the data that has been inputted
		 * If all checks pass then the data is assigned to the data model and the window moves onto the next section for the user to complete
		 * The module information for the selected course will also be passed to the relevant views so that the user can select the ones they would like to do
		 */
		@Override
		public void handle(ActionEvent event){
			String errorMessage="";
			String pNumb=studentProfile.getStudentPnumber();
			String studentEmail=studentProfile.getStudentEmail();

			if((pNumb.charAt(0)=='P')||(pNumb.charAt(0)=='p')){
				errorMessage+="Student P Number does not need to include the 'P'.\n";
			}
			if(studentEmail.indexOf("@")==-1){ 
				errorMessage+="Please enter a proper email address.\n";
			}
			if(studentProfile.getStudentDate()==null){ 
				errorMessage+="Please enter a valid date.";
			}
			
			if(errorMessage!=""){
				new Message("Error","Insufficient Data",errorMessage,AlertType.ERROR);
			}else{
				model.getAllSelectedModules().clear();
				model.getAllReservedModules().clear();
				model.setStudentCourse(studentProfile.getSelectedCourse());
				model.setStudentName(studentProfile.getStudentName());
				model.setStudentPnumber(pNumb);
				model.setStudentEmail(studentEmail);
				model.setSubmissionDate(studentProfile.getStudentDate());

				reservedModules.getYearLongModules().clearModules();
				reservedModules.getTermOneModules().clearModules();
				reservedModules.getTermTwoModules().clearModules();
				reservedModules.isAccordianDisabled(true);
				overviewPane.clearText();

				updateModules(selectedModules,false);

				selectedModules.setExpandedPane(0);
				view.changeTab(1);
			}
		}
	}

	/**
	 * ModulePanesResetBtnHandler
	 * Private class is an event handler that can be attached to the reset button within the ModulePanes class
	 * It can be used whether the module pane shows the selected modules or reserved modules
	 */
	private class ModulePanesResetBtnHandler implements EventHandler<ActionEvent>{

		private ModulePanes view;
		private Boolean isReserved;

		/**
		 * ModulePanesResetBtnHandler
		 * @param view - the accociated ModulePane that the button is connected to
		 * @param isReserved - boolean whether the view connected is the reserved pane or not
		 * Constructor makes the passed values globally available to the entire private class so that they can be used within functions
		 */
		public ModulePanesResetBtnHandler(ModulePanes view,Boolean isReserved){
			this.view=view;
			this.isReserved=isReserved;
		}

		/**
		 * handle
		 * @param event
		 * Method clears all of the connected panes to the passed view
		 * Gets a collection of all the modules connected to the course that the user has selected
		 * Checks if the isReserved variable is true, if the variable is true then it runs the private method getUnusedModules
		 * Else it goes through all of the modules within the collection checking their runplan and then if they are mandatory
		 * It attaches each of the modules to their relevant listviews within the respective view panes.
		 */
		@Override
		public void handle(ActionEvent event){
			updateModules(view,isReserved);
		}
	}

	/**
	 * SelectedModulesSubmitBtnHandler
	 * Private class designed specifically for the Selected Modules pane submit button
	 * Checks that the credit requirements have been reached and then adds the modules selected by the user to the model
	 */
	private class SelectedModulesSubmitBtnHandler implements EventHandler<ActionEvent>{

		/**
		 * handle
		 * @param event
		 * Method checks if the credit requirements for the course have been reached. 
		 * If they have not been reached then an error message is shown to the user stating that there should be 60 credits per module
		 * Providing the total of 120 credits has been reached then the method adds all of the selected modules to the model
		 * Once they have been added it adds all of the none selected modules and compulsoary modules to the reserved modules pane
		 */
		@Override
		public void handle(ActionEvent event){
			if(((selectedModules.getTermOneModules().getCredits())!=60)&&((selectedModules.getTermTwoModules().getCredits())!=60)){
				new Message("Error","Insufficent Modules","You have not met the credits requirements for this course.\nPlease select enough mdoules to reach 60 credits per term.",AlertType.ERROR);
			}else{
				this.addToProfile(selectedModules.getYearLongModules().getSubmittedModules());
				this.addToProfile(selectedModules.getTermOneModules().getSubmittedModules());
				this.addToProfile(selectedModules.getTermTwoModules().getSubmittedModules());

				updateModules(reservedModules,true);
				reservedModules.setExpandedPane(0);
				reservedModules.isAccordianDisabled(false);
				view.changeTab(2);
			}
		}

		/**
		 * addToProfile
		 * @param selectedModules - list of modules to be added to the model
		 * Method goes through a for loop, adding all of the modules selected by the user to the model
		 */
		private void addToProfile(ObservableList<Module> selectedModules){
			for(Module module:selectedModules){
				model.addSelectedModule(module);
			}
		}
	}

	/**
	 * ReservedModulesSubmitHandler
	 * private class is a handler implementation for the submit button displayed on the reserved modules tab pane
	 */
	private class ReservedModulesSubmitHandler implements EventHandler<ActionEvent>{

		/**
		 * handle
		 * @param event
		 * Method checks if the minimum number credits for the course has been reached, if they have not been reached then an error message is displayed to the user
		 * If the minimum number of credits has been reached then the program will add the modules to the model
		 * Once all of the modules have been added the method adds all the necessary data to the overview pane
		 */
		@Override
		public void handle(ActionEvent event){
			if(((reservedModules.getTermOneModules().getCredits())!=60)&&((reservedModules.getTermTwoModules().getCredits())!=60)){
				new Message("Error","Insufficient Modules","You have not met the credit requirements for this course.\nPlease select enough to reach the 60 credits per term.",AlertType.ERROR);
			}else{
				this.addToProfile(reservedModules.getYearLongModules().getSubmittedModules());
				this.addToProfile(reservedModules.getTermOneModules().getSubmittedModules());
				this.addToProfile(reservedModules.getTermTwoModules().getSubmittedModules());

				overviewPane.setProfileText("Name: "+model.getStudentName().getFullName()+"\nPnumber: P"+model.getStudentPnumber()+"\nEmail: "+model.getStudentEmail()+"\nDate: "+model.getSubmissionDate().toString()+"\nCourse: "+model.getStudentCourse().getCourseName());
				overviewPane.setSelectedModules("Selected Modules:\n========="+generateModuleDetails(model.getAllSelectedModules()));
				overviewPane.setReservedModules("Reserved Modules:\n========="+generateModuleDetails(model.getAllReservedModules()));

				view.changeTab(3);
			}
		}

		/**
		 * addToProfile
		 * @param reservedModules - collection of modules passed to the private method
		 * Method goes through each of the modules within the passed collection adding them to the models reserved modules
		 */
		private void addToProfile(ObservableList<Module> reservedModules){
			for(Module module:reservedModules){
				model.addReservedModule(module);
			}
		}
	}

	/**
	 * MenuBarSaveHandler
	 * Private class is the event handler implementation for the save button that is within the programs menu bar
	 */
	private class MenuBarSaveHandler implements EventHandler<ActionEvent>{

		/**
		 * handle
		 * @param event
		 * Method checks that the model has had user information saved to it
		 * If no user information is saved to the model that an error is produced and shown to the user. No data is saved to a file
		 * If the model has data saved to it then the data that is saved to the file and stored within a specified location by the program
		 * There is a chance for the process of saving the file to error out while saving the file, so there is a try catch placed around saving the file 
		 * This prevents the process from crashing the entire program and can instead display an error message to the user, then the program can continue as nromal
		 */
		@Override
		public void handle(ActionEvent event){
			if((model.getSubmissionDate()==null)&&(model.getStudentCourse()==null)){
				new Message("Error","No Saved User Information","Before saving your progress please make sure to have completed the user profile.",AlertType.ERROR);
			}else{
				try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/savedProfile.dat"));){
					oos.writeObject(model);
					oos.flush();
					new Message("Save File","File Saved","Data was saved to file: savedProfile.dat",AlertType.INFORMATION);
				}catch(IOException error){
					new Message("Error","Saving File Failed","Data could not be written to file.\nError: "+error.toString(),AlertType.ERROR);
				}
			}
		}
	}

	/**
	 * MenuBarLoadHandler
	 * Private class is the event handler implementation for the load button that is within the programs menu bar
	 * It uses a single public method called handle which is the event handler itself 
	 * There are also some private methods that aid in the event handler in completing its desired task.
	*/
	private class MenuBarLoadHandler implements EventHandler<ActionEvent>{

		/**
		 * handle
		 * @param event
		 * Method tries to read the desired file from the program specified location, this is contained within a try catch method
		 * if there is not a file there then the catch method is triggered displaying an error message to the user
		 * Once the file has been read into the program then it loads in all of the modules that can be selected by the user 
		 * If some modules have already been selected by the user then they are added to the selected area automatically as it goes through them all
		 * Then checks if the model has selected modules already saved
		 * Yes, then the program loads in all reserved modules into the correct columns within the pane, if the model already has reserved modules saved then the display is changed to the overview pane
		 * No, program changes the displayed pane to the selected modules pane
		 */
		@Override
		public void handle(ActionEvent event){
			try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("data/savedProfile.dat"));){
				model=(StudentProfile)ois.readObject();
			}catch(IOException error){
				new Message("Error","Loading File Saved","Data could not be read from file.\nError: "+error.toString(),AlertType.ERROR);
			}catch(ClassNotFoundException error){
				new Message("Error","Loading File Saved","Data could not be read from file.\nError: "+error.toString(),AlertType.ERROR);
			}

			updateModules(selectedModules,false);

			if(model.getAllSelectedModules().isEmpty()){
				view.changeTab(1);
				selectedModules.isAccordianDisabled(false);
				selectedModules.setExpandedPane(0);
			}else{
				updateModules(reservedModules,true);

				if(model.getAllReservedModules().isEmpty()){
					reservedModules.setExpandedPane(0);
					reservedModules.isAccordianDisabled(false);
					view.changeTab(2);
				}else{
					selectedModules.isAccordianDisabled(false);
					reservedModules.isAccordianDisabled(false);
					reservedModules.triggerSubmitButton();
					view.changeTab(3);
				}
			}
		}
	}

	/**
	 * OverviewSelectionSaveHandler
	 * Private class is the event handler implementation for the save button within the overview pane 
	 * It uses a single public method called handle which is the event handler itself
	 * There are also private methods used to help the public method achieve its goal
	 */
	private class OverviewSelectionSaveHandler implements EventHandler<ActionEvent>{

		/**
		 * handle
		 * @param event
		 * Method generate a String containing all the details that have been saved to the model
		 * It utilises a private method to go through all the different modules within the selected & reserved 
		 * Once the String has been generated it is saved to a file.
		 */
		@Override
		public void handle(ActionEvent event){
			String dataSave="Name: "+model.getStudentName().getFullName()+"\nPnumber: P"+model.getStudentPnumber()+"\nEmail: "+model.getStudentEmail()+"\nDate: "+model.getSubmissionDate().toString()+"\nCourse: "+model.getStudentCourse().getCourseName()+"\n\n\nSelected Modules:\n========="+generateModuleDetails(model.getAllSelectedModules())+"\n\nReserved Modules:\n========="+generateModuleDetails(model.getAllReservedModules());

			try{
				this.saveToFile(dataSave);
			}catch(FileNotFoundException error){
				new Message("Error","File Not Found","Error occured while saving the overview to a text file. Error: "+error.toString(),AlertType.ERROR);
			}
		}

		/**
		 * saveToFile
		 * @param dataSave - string to write to the generated file
		 * @throws FileNotFoundException - error if the user decides to exit out of the file selection screen
		 * Method saves the passed string to a text file within a specified location by the program.
		 */
		private void saveToFile(String dataSave) throws FileNotFoundException{
			FileChooser fileLocation=new FileChooser();
			fileLocation.setInitialFileName(model.getStudentPnumber()+".txt");
			fileLocation.setInitialDirectory(new File("data"));
			File storedFile=fileLocation.showSaveDialog(null);
			PrintWriter textOutput=new PrintWriter(storedFile);

			if(storedFile!=null){
				textOutput.println(dataSave);
			}

			textOutput.close();
		}
	}
}