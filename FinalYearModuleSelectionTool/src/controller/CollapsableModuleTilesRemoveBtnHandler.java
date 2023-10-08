package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import model.Module;
import view.CollapsableModuleTiles;
import view.Message;

/**
 * CollapsableModuleTilesRemoveBtnHandler
 * Event handler for the remove buttons used within teh CollapsableModuleTiles class 
 * It has been designed to allow for the same handler class to be applied to almost every instance of the button
 */
public class CollapsableModuleTilesRemoveBtnHandler implements EventHandler<ActionEvent>{

    private CollapsableModuleTiles tabPaneTile;

    /**
     * CollapsableModuleTilesRemoveBtnHandler
     * @param tabPaneTile - the specific class that the handler is connected to and will be affecting 
     * Constructor is used to pass the class that the button is within allowing for all controls to be done through the event handler class
     * Having the class be passed in means that the same event handler class can be used for multiple buttons but change values only within the view that has been passed to it
     */
    public CollapsableModuleTilesRemoveBtnHandler(CollapsableModuleTiles tabPaneTile){
        this.tabPaneTile=tabPaneTile;
    }

    /**
     * handle
     * @param event
     * Method is used to remove moudles that the user has selected so they are noy logner signed onto them
     * It checks that a mdoule has been selefcted by the user from within the selected modules area, if a module has not been selected then an error message will be shown to the user asking for them to select a module
     * If the module they have selected is a compulsary module then an error message will be shown saying that the module cannot be removed as it is a compulsary module.
     * Providing that it is not a compulsory module then it will be removed from the modules the user would like to do and added back to the unselected modules area for the user to pick again if they so wished.
     */
    @Override
    public void handle(ActionEvent event){
        if(tabPaneTile.getSelectedSelectModule()==null){
            new Message("Error","Item Not Selected","Please select a module that you would no longer like to register for.\nIf no modules are appearing then please make sure you have completed your student profile.",AlertType.ERROR);
        }else if(tabPaneTile.getSelectedSelectModule().isMandatory()){
            new Message("Error","Module Compulsorary","The selected module cannot be removed as it is a compulsorary module. Please select another module.",AlertType.ERROR);
        }else{
            Module removingModule=tabPaneTile.getSelectedSelectModule();
            tabPaneTile.removeModuleSelected(removingModule);
            tabPaneTile.addUnselectedModule(removingModule);
        }
    }
}