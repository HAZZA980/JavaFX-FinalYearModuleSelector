package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

/**
 * CollapsableModuleTiles
 * Class makes all of the different expanding panes within the selected & reserved module section
 * Has been designed so the same layout for the pane can be used to represent different terms 
 */
public class CollapsableModuleTiles extends BorderPane{

    private final int maxCredits=60;

    private SimpleIntegerProperty credits;
    private Button btnAdd,btnRemove;
    private ListView<Module> lsvUnSelectedModules,lsvSelectedModules;
    private ObservableList<Module> oblUnSelectedModules,oblSelectedModules;
    
    /**
     * CollapsableModuleTiles
     * Constructor generates the main design of the view and creates all of the different elements show
     * It also adds all of the elements to the BorderPane view that this class extends
     */
    public CollapsableModuleTiles(){
        Label lblUnSelectedModules=new Label("Unselected Modules");
        Label lblSelectedModules=new Label("Selected Modules");
        oblUnSelectedModules=FXCollections.observableArrayList();
        oblSelectedModules=FXCollections.observableArrayList();
        lsvUnSelectedModules=new ListView<Module>(oblUnSelectedModules);
        lsvSelectedModules=new ListView<Module>(oblSelectedModules);
        credits=new SimpleIntegerProperty(0);

        HBox selectionLayout=new HBox(linkLabelView(lblUnSelectedModules,lsvUnSelectedModules),linkLabelView(lblSelectedModules,lsvSelectedModules));
        selectionLayout.setAlignment(Pos.CENTER);
        VBox.setVgrow(lsvUnSelectedModules,Priority.ALWAYS);
        VBox.setVgrow(lsvSelectedModules,Priority.ALWAYS);
        this.setCenter(selectionLayout);
        this.setBottom(generateButtons());
    }

    /**
     * linkLabelView
     * @param title - Label to be assigned to the accossiated ListView
     * @param view - ListView the storing all of the data connected to the Label
     * @return - VBox that has combined the Label and ListView into a single elements that can be added to the view
     * Private method sets the preffered settings for the Label and Listview and then combines them together 
     * They are combined so that they can be added as a single elements to the view 
     */
    private VBox linkLabelView(Label title,ListView<Module> view){
        view.setPrefSize(280,150);

        VBox container=new VBox();
        container.getChildren().addAll(title,view);
        container.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(container,Priority.ALWAYS);
        return container;
    }

    /**
     * generateButtons
     * @return - HBox storing buttons to be displayed within the view
     * Method combines the add and remove button into a single element that can then be added to the view
     * The preferred settings for the combined element are set upon its creation.
     */
    private HBox generateButtons(){
        btnAdd=createButtons("Add");
        btnAdd.setTooltip(new Tooltip("Adds selected module to selected modules list"));
        btnRemove=createButtons("Remove");
        btnRemove.setTooltip(new Tooltip("Removes the selected module from the selected modules list"));

        HBox box=new HBox(btnAdd,btnRemove);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10,10,10,10));
        box.setSpacing(20);
        return box;
    }

    /**
     * createButtons
     * @param title - the text to be displayed on the button
     * @return - the completed button
     * Method creates a button, sets the buttons title and desired size
     * The completed button is the returned
     */
    private Button createButtons(String title){
        Button btn=new Button(title);
        btn.setPrefSize(80,10);
        return btn;
    }

    /**
     * setAddButtonHandler
     * @param handler - event handler to be connected to the add button
     * Attaches the passed event handler to the created add button within the class
     */
    public void setAddButtonHandler(EventHandler<ActionEvent> handler){
        btnAdd.setOnAction(handler);
    }

    /**
     * setREmoveButtonHandler
     * @param handler - event handler to be connected to the remove button
     * Attaches the passed event handler to the created remove button within the class
     */
    public void setRemoveButtonHandler(EventHandler<ActionEvent> handler){
        btnRemove.setOnAction(handler);
    }

    /**
     * add SelectedModule
     * @param module - the passed module to be added
     * This method checks that if the module was added that it would not go over the maximum credit count for the term
     * If it does go over the maximum credit count for the year then an error message is displayed to the user
     * If it does not go over the maximum credit count then it is added to the selected modules listview
     */
    public void addSelectedModule(Module module){
        oblSelectedModules.add(module);
        lsvSelectedModules.getSelectionModel().clearSelection();
        this.addCredits(module.getModuleCredits());
    }

    /**
     * getSelectedSelectModule
     * @return - returns the currently selected select module
     * This gets the module that is stored within the selected modules listview that the user has currently selected
     */
    public Module getSelectedSelectModule(){
        return lsvSelectedModules.getSelectionModel().getSelectedItem();
    }

    /**
     * removeModuleSelected
     * @param module - the passed module to be removed
     * This removes the passed movudle from the selected modules listview and clears the current selection of that listview
     * The method also removes the credits that the added module would have given to the term
     */
    public void removeModuleSelected(Module module){
        oblSelectedModules.remove(module);
        lsvSelectedModules.getSelectionModel().clearSelection();
        this.addCredits(-(module.getModuleCredits()));
    }

    /**
     * addUnselectedModule
     * @param module - the passed module to be added
     * This method addeds the passed module to the unselected modules listview and clears the currently selected item
     */
    public void addUnselectedModule(Module module){
        oblUnSelectedModules.add(module);
        lsvUnSelectedModules.getSelectionModel().clearSelection();
    }

    /**
     * getSelectedUnSelectedModule
     * @return - returns the currently selected unselected module
     * This gets the module that is stored within the unselected modules listview that the user has currently selected 
     */
    public Module getSelectedUnSelectedModule(){
        return lsvUnSelectedModules.getSelectionModel().getSelectedItem();
    }

    /**
     * removeModuleUnSelected
     * @param module - the passed module to be removed
     * This method removed the passed module from the unselected modules listview and clears the currently selected item
     */
    public void removeModuleUnSelected(Module module){
        oblUnSelectedModules.remove(module);
        lsvUnSelectedModules.getSelectionModel().clearSelection();
    }

    /**
     * getSubmittedModules
     * @return - returns list of modules
     * Method returns the list of modules that the user has selected
     */
    public ObservableList<Module> getSubmittedModules(){
        return oblSelectedModules;
    }

    /**
     * getCredits
     * @return - returns the current number of credits selected
     */
    public int getCredits(){
        return credits.intValue();
    }

    /**
     * addCredits
     * @param amount - the amount to add
     * Adds a given amount to the current number of credits 
     * The amount given can be negative to instead decrease the number of credits selected by the user.
     */
    public void addCredits(int amount){
        credits.setValue((credits.intValue())+amount);
    }

    /**
     * setCreditsChangeListener
     * @param listener - the passed listener
     * Method attaches a change listener to the simple integer property credits
     */
    public void setCreditsChangeListener(ChangeListener<Number> listener){
        credits.addListener(listener);
    }

    /**
     * getMaxCredits
     * @return - returns max number credits the term can have
     * Allows other sections of the program to know the max number of credits
     */
    public int getMaxCredits(){
        return maxCredits;
    }

    /**
     * clearModules
     * selected and unselected modules list are cleared, removing all of the passed modules
     * Sets the default number of credits to 0
     */
    public void clearModules(){
        oblUnSelectedModules.clear();
        oblSelectedModules.clear();
        credits.setValue(0);
    }
}
