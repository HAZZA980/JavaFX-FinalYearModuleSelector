package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import model.Module;
import view.CollapsableModuleTiles;
import view.Message;

/**
 * CollapsableModuleTilesAddBtnHandler
 * Event handler for the add buttons used within the CollapsableModuleTiles class
 * It has been designed to allow for the same handler class to be applied to almost every insatance of the button
 */
public class CollapsableModuleTilesAddBtnHandler implements EventHandler<ActionEvent>{

    private CollapsableModuleTiles tabPaneTile;

    /**
     * CollapsableModuleTilesAddBtnHandler
     * @param tabPaneTile - the specific class that the handler is connected to and will be affecting 
     * Constructor is used to pass the class that the button is within allowing for all controls to be done through the event handler class
     * Having the class be passed in means that the same event handler class can be used for multiple buttons but change values only within the view it is connected to.
     */
    public CollapsableModuleTilesAddBtnHandler(CollapsableModuleTiles tabPaneTile){
        this.tabPaneTile=tabPaneTile;
    }

    /**
     * handle
     * @param event
     * Method checks that an item had been selected out of the modules the user is yet to sign up for
     * If a module has not been selected then it shows an error message, informing the user that they will need to select a moudle from the unselected list to use this button
     * If a module has been selected by the user then it is added to the modules the user has selected and removed from the modules the user has not selected. 
     */
    @Override
    public void handle(ActionEvent event){
        if(tabPaneTile.getSelectedUnSelectedModule()==null){
            new Message("Error","Item Not Selected","Please select a moudle you would like to register for.\nIf no modules are appearing then please make sure you have completed your student profile.",AlertType.ERROR);

        }else{
            Module addingModule=tabPaneTile.getSelectedUnSelectedModule();
            int currentCredits=tabPaneTile.getCredits();
            int maxCredits=tabPaneTile.getMaxCredits();

            if((currentCredits+addingModule.getModuleCredits())>maxCredits){
                new Message("Error","Credits Amount Exceeded","Adding this module would exceed the amount of allowed credits. Please remove an already selected module in order to add this one",AlertType.ERROR);
            }else{
                tabPaneTile.addSelectedModule(addingModule);
                tabPaneTile.removeModuleUnSelected(addingModule);
            }
        }
    }
}