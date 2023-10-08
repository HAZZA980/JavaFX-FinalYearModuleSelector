package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * OverviewPane
 * Class for the overview of all other elements 
 * Methods allow for the setting of data that can be shown and the attachment of handlers for buttons
 */
public class OverviewPane extends BorderPane{
    
    private Button btnSave;
    private TextArea txtProfileOverview,txtSelectedModules,txtReservedModules;

    /**
     * OverviewPane
     * Constructor method used to create the main design of the pane
     * All private variables are instantiated within the method
     */
    public OverviewPane(){
        Label lblProfile=new Label("Profile");
        Label lblSelected=new Label("Selected Modules");
        Label lblReserved=new Label("Reserved Modules");
        txtProfileOverview=new TextArea("Profile will appear here once it has been created");
        txtSelectedModules=new TextArea("Selected modules will appear here once they have been submitted");
        txtReservedModules=new TextArea("Reserved modules will appear here once they have been submitted");
        btnSave=new Button("Save");
        
        lblProfile.setAlignment(Pos.TOP_LEFT);
        VBox profileLayout=new VBox(lblProfile,txtProfileOverview);
        txtProfileOverview.setPrefHeight(120);
        txtProfileOverview.setEditable(false);
        profileLayout.setPadding(new Insets(10,20,10,20));

        HBox modulesLayout=new HBox(generateModulesBox(lblSelected,txtSelectedModules),generateModulesBox(lblReserved,txtReservedModules));
        modulesLayout.setPadding(new Insets(10,20,10,20));
        modulesLayout.setSpacing(20);
        modulesLayout.setAlignment(Pos.CENTER);

        VBox.setVgrow(txtSelectedModules,Priority.ALWAYS);
        VBox.setVgrow(txtReservedModules,Priority.ALWAYS);

        this.setTop(profileLayout);
        this.setCenter(modulesLayout);
        this.setBottom(generateButtons());
    }

    /**
     * generateModulesBox
     * @param title - this is the label for the textarea/textfield
     * @param text - the passed textarea 
     * @return - VBox containing the passed elements with pre-applied parameters/settings
     * Method combines the 2 passed arguments into a single VBox that then has settings applied and is returned
     */
    private VBox generateModulesBox(Label title,TextArea text){
        VBox container=new VBox(title,text);
        text.setPrefSize(280,150);
        text.setEditable(false);
        HBox.setHgrow(container,Priority.ALWAYS);
        return container;
    }

    /**
     * generateButtons
     * @return - returns HBox
     * Method generates a HBox containing the save button for the pane
     * All setting for the button are applied wihtin this method
     */
    private HBox generateButtons(){
        btnSave.setPrefSize(80,10);

        HBox layout=new HBox(btnSave);
        layout.setSpacing(20);
        layout.setPadding(new Insets(10,10,30,10));
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    /**
     * setSaveButtonHandler
     * @param handler - passed event handler
     * Method attaches the event handler passed to the save button
     */
    public void setSaveButtonHandler(EventHandler<ActionEvent> handler){
        btnSave.setOnAction(handler);
    }

    /**
     * setProfileText
     * @param text - passed string value
     * Method sets the profile textfield to the passed string
     */
    public void setProfileText(String text){
        txtProfileOverview.setText(text);
    }

    /**
     * setSelectedModules
     * @param text - passed string value
     * Method sets the selected modules textfield to the passed string
     */
    public void setSelectedModules(String text){
        txtSelectedModules.setText(text);
    }

    /**
     * setReservedModules
     * @param text - passed string value
     * Method sets the reserved modules textfield to the passed string
     */
    public void setReservedModules(String text){
        txtReservedModules.setText(text);
    }

    /**
     * clearText
     * Method resets the text displayed wihtin the textfields on the view.
     */
    public void clearText(){
        txtProfileOverview.setText("Profile will appear here once it has been created");
        txtSelectedModules.setText("Selected modules will appear here once they have been submitted");
        txtReservedModules.setText("Reserved modules will appear here once they have been submitted");
    }
}
