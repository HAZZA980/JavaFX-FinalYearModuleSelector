package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * ModulePanes
 * class used to create instances of panes used for selecting different modules
 * The modules that the user is able to pick from are set by the controller. 
 * The class itself only handles displaying all the information.
 */
public class ModulePanes extends BorderPane{
    
    private Accordion collapsableLayout;
    private CollapsableModuleTiles yearLongModules,termOneModules,termTwoModules;
    private Button btnReset,btnSubmit;
    private TextField txtTerm1Credits,txtTerm2Credits;

    /**
     * ModulePanes
     * Constructor initilises all of the global variables
     * The designed of the pane is then laid out
     * It utilises private methods/functions to help create certain areas of the program where possible
     */
    public ModulePanes(){
        collapsableLayout=new Accordion();
        yearLongModules=new CollapsableModuleTiles();
        termOneModules=new CollapsableModuleTiles();
        termTwoModules=new CollapsableModuleTiles();

        collapsableLayout.getPanes().addAll(new TitledPane("Year Long Modules",yearLongModules),new TitledPane("Term One Modules",termOneModules),new TitledPane("Term Two Modules",termTwoModules));
        collapsableLayout.setExpandedPane(null);

        this.isAccordianDisabled(true);

        btnReset=new Button("Reset");
        btnReset.setTooltip(new Tooltip("Rmoves all the currently selected modules and resets the pane to its default state"));
        btnSubmit=new Button("Submit");
        btnSubmit.setTooltip(new Tooltip("Save the currently selected modules to the students created profile")); 
        Label lblTerm1Credits=new Label("Term 1 Credits: ");
        Label lblTerm2Credits=new Label("Term 2 Credits: ");
        txtTerm1Credits=generateTxtField("0");
        txtTerm2Credits=generateTxtField("0");

        HBox buttonLayout=new HBox(btnReset,btnSubmit);
        btnReset.setPrefSize(80,10);
        btnSubmit.setPrefSize(80,10);
        buttonLayout.setSpacing(10);
        buttonLayout.setAlignment(Pos.CENTER);

        HBox creditsLayout=new HBox(new HBox(lblTerm1Credits,txtTerm1Credits),new HBox(lblTerm2Credits,txtTerm2Credits));
        creditsLayout.setSpacing(80);
        creditsLayout.setAlignment(Pos.CENTER);

        VBox bottomWindowLayout=new VBox(creditsLayout,buttonLayout);
        bottomWindowLayout.setSpacing(10);
        bottomWindowLayout.setPadding(new Insets(10,10,10,10));

        this.setCenter(collapsableLayout);
        this.setBottom(bottomWindowLayout);
    }

    /**
     * generateTxtField
     * @param value - Passed string to be placed wihtin the textfield
     * @return - returns textfield 
     * Method accepts passed string to enter into the text field, it then sets the values of the textfiled
     * Once those values have been set it is passed back
     */
    private TextField generateTxtField(String value){
        TextField txt=new TextField(value);
        txt.setEditable(false);
        txt.setPrefSize(40,10);
        return txt;
    }

    /**
     * getYearLongModules
     * @return - returns the CollapsableModuleTiles instance
     * Method passes/returns the CollapsableModuletiles instance yearLongModules
     */
    public CollapsableModuleTiles getYearLongModules(){
        return yearLongModules;
    }

    /**
     * getTermOneModules
     * @return - returns the CollapsableModuleTiles instance
     * Method passes/returns the CollapsableModuletiles instance termOneModules
     */
    public CollapsableModuleTiles getTermOneModules(){
        return termOneModules;
    }

    /**
     * getTermTwoModules
     * @return - returns the CollapsableModuleTiles instance
     * Method passes/returns the CollapsableModuletiles instance termTwoModules
     */
    public CollapsableModuleTiles getTermTwoModules(){
        return termTwoModules;
    }

    /**
     * setResetButtonHandler
     * @param handler - passed event handler
     * Method attaches the passed event handler to the reset buttons
     */
    public void setResetButtonHandler(EventHandler<ActionEvent> handler){
        btnReset.setOnAction(handler);
    }

    /**
     * setSubmitButtonHandler
     * @param handler - passed event handler
     * Method attaches the passed event handler to the submit button
     */
    public void setSubmitButtonHandler(EventHandler<ActionEvent> handler){
        btnSubmit.setOnAction(handler);
    }

    /**
     * triggerSubmitButton
     * Allows the submit button to be triggered by the controller
     */
    public void triggerSubmitButton(){
        btnSubmit.fire();
    }

    /**
     * isAccordianDisabled
     * @param status - passed boolean value 
     * Method accepts a boolean value, then applied that boolean value to all of the titled panes
     * This disables/enabled the panes depending on the passed value
     */
    public void isAccordianDisabled(Boolean status){
        for(TitledPane pane:collapsableLayout.getPanes()){
            pane.setDisable(status);
        }
    }

    /**
     * setExpandedPane
     * @param index - passed integer value
     * Method accepts integer value which dictates which collapsable pane should be expanded
     */
    public void setExpandedPane(int index){
        collapsableLayout.setExpandedPane(collapsableLayout.getPanes().get(index));
    }

    /**
     * setTerm1Credits
     * @param credits - passed integer representing the number credits 
     * Method accepts integer and sets it to the text field for term 1 credits
     */
    public void setTerm1Credits(int credits){
        txtTerm1Credits.setText(""+credits);
    }

    /**
     * setTerm2Credits
     * @param credits - passed integer representing the number credits 
     * Method accepts integer and sets it to the text field for term 2 credits
     */
    public void setTerm2Credits(int credits){
        txtTerm2Credits.setText(""+credits);
    }
}
