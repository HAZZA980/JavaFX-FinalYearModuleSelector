package view;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Course;
import model.Name;

/**
 * CreateStudentProfilePane
 * Class pane to allow users to create a profile for the application
 */
public class CreateStudentProfilePane extends GridPane {

	private ComboBox<Course> cboCourses;
	private DatePicker inputDate;
	private TextField txtFirstName, txtSurname,  txtPnumber, txtEmail;
	private Button btnCreateProfile;

	/**
	 * CreateStudentProfilePane
	 * Constructor creates the overall design of the pane
	 * Also initialises all of the global parameters
	 */
	public CreateStudentProfilePane() {
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.RIGHT);

		this.getColumnConstraints().addAll(column0);
		
		Label lblTitle = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstName = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");
		
		cboCourses = new ComboBox<Course>();
		
		txtFirstName = new TextField();
		txtSurname = new TextField();
		txtPnumber = new TextField();
		txtEmail = new TextField();
		
		inputDate = new DatePicker();
		
		btnCreateProfile = new Button("Create Profile");
		btnCreateProfile.setDisable(true);

		this.add(lblTitle, 0, 0);
		this.add(cboCourses, 1, 0);
		this.add(lblPnumber, 0, 1);
		this.add(txtPnumber, 1, 1);
		this.add(lblFirstName, 0, 2);
		this.add(txtFirstName, 1, 2);
		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);
		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);
		this.add(lblDate, 0, 5);
		this.add(inputDate, 1, 5);
			
		this.add(new HBox(), 0, 6);
		this.add(btnCreateProfile, 1, 6);
	}
	
	/**
	 * addCourseDataToComboBox
	 * @param courses - a passed array of courses
	 * The method goes through all of the passed courses and adds them to a combobox
	 * Once all the courses have been added it selects the first one within the combobox
	 */
	public void addCourseDataToComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0);
	}
	
	/**
	 * getSelectedCourse
	 * @return - returns the currently selected course
	 * The method returns the course that is currently selected by the user
	 */
	public Course getSelectedCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * getStudentPnumber
	 * @return - returns Pnumber
	 * Method returned the string value entered into the Pnumber textfield by the user
	 */
	public String getStudentPnumber() {
		return txtPnumber.getText();
	}
	
	/**
	 * getStudentName
	 * @return - returns a name value
	 * Method combines the entered first name and last name from the user into a single value
	 * The value is the returned
	 */
	public Name getStudentName() {
		return new Name(txtFirstName.getText(), txtSurname.getText());
	}

	/**
	 * getStudentEmail
	 * @return - returns a string
	 * Method returns the string enter into the txtEmail field by ther user
	 */
	public String getStudentEmail() {
		return txtEmail.getText();
	}
	
	/**
	 * getStudentDate
	 * @return - returns date
	 * Method returns the date entered by the user
	 */
	public LocalDate getStudentDate() {
		return inputDate.getValue();
	}
	
	/**
	 * addDisableCreateProfilebtn
	 * @param boolBind - passed boolean binding
	 * Method attaches the passed boolean binding property to the create profile button
	 */
	public void addDisableCreateProfilebtn(BooleanBinding boolBind){
		btnCreateProfile.disableProperty().bind(boolBind);
	}

	/**
	 * areFieldsComplete
	 * @return - returns a boolean binding 
	 * Method compares the different fields within the view and returns a boolean binding based on those comparisons
	 */
	public BooleanBinding areFieldsComplete(){
		return (txtFirstName.textProperty().isEmpty().or(txtSurname.textProperty().isEmpty())).or(txtPnumber.textProperty().isEmpty().or(txtEmail.textProperty().isEmpty()));
	}
	
	/**
	 * addCreateStudentProfileHandler
	 * @param handler - the passed event handler
	 * Method attaches the passed event handler to the create profile button
	 */
	public void addCreateStudentProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreateProfile.setOnAction(handler);
	}

	/**
	 * setUserDetails
	 * @param pNumber - specifies the pNumber that the text area should be set to
	 * @param firstName - specifies the first name that should be displayed within the text area
	 * @param surName - specifies the surname that should be displayed within the text area
	 * @param studentEmail - specifies the student email that should be displayed within the text area
	 * @param submittedDate - specifies the date that should be shown
	 * @param courseSelected - specifies the course that should be selected
	 * Method sets all of the values to their different display areas so the user can see the data that has been enetered
	 */
	public void setUserDetails(String pNumber,String firstName,String surName,String studentEmail,LocalDate submittedDate,int courseSelected){
		txtPnumber.setText(pNumber);
		txtFirstName.setText(firstName);
		txtSurname.setText(surName);
		txtEmail.setText(studentEmail);
		inputDate.setValue(submittedDate);
		cboCourses.getSelectionModel().select(courseSelected);
	}

}
