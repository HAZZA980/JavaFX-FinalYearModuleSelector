package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.RunPlan;
import view.ModulePanes;

/**
 * CreditsChangeListener
 * Change listener for the simple integer property of credits within the CollapsableModuleTiles instacnes
 * Allows for the tracking of changes to that value so that it can be represented within the ModulePanes view passed to the handler
 */
public class CreditsChangeListener implements ChangeListener<Number>{

    private ModulePanes view;
    private RunPlan runPlan;

    /**
     * CreditsChangeListener
     * @param view - the ModulePane that the CollapsableModuleTiles instance is within
     * @param runPlan - the run plan for the connected CollapsableModuleTiles instance
     * Constructor to initialise globallu availble variables within the private class
     */
    public CreditsChangeListener(ModulePanes view,RunPlan runPlan){
        this.view=view;
        this.runPlan=runPlan;
    }

    /**
     * changed
     * @param observable
     * @param oldValue - previous value before it changes
     * @param newValue - the new value that it has changed to
     * Method notices that the credits value has changed, checks what the run plan attched to those credits is
     * Depening on the run plan assigns the credits to the appropriate CollapsableModuleTiles instance within the declared view
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
        int value=(int)newValue;
        switch(runPlan){
            case YEAR_LONG:
                int halfValue=value/2;
                view.getTermOneModules().addCredits(halfValue);
                view.getTermTwoModules().addCredits(halfValue);
                break;
            case TERM_1:
                view.setTerm1Credits(value);
                break;
            case TERM_2:
                view.setTerm2Credits(value);
                break;
        }
    }
}