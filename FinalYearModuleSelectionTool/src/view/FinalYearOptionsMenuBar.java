package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

/**
 * FinalYearOptionsMenuBar
 * Class handles the creation of the menu bar for the entire application
 * This includes the items stored within the menu bar and their shorcuts
 * Class also has method to add the relevant event handlers for the menu bar items
 */
public class FinalYearOptionsMenuBar extends MenuBar {

	private MenuItem saveItem, loadItem, aboutItem, exitItem;

	/**
	 * FinalYearOptionsMenuBar
	 * Constructor creates the menu bar and adds all of the different items to the displayed menu bar
	 * Also adds the different shortcuts to the menu items
	 */
	public FinalYearOptionsMenuBar() { 

		Menu menu;

		menu = new Menu("_File");

		loadItem = new MenuItem("_Load Student Data");
		loadItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		menu.getItems().add(loadItem);

		saveItem = new MenuItem("_Save Student Data");
		saveItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		menu.getItems().add(saveItem);

		menu.getItems().add( new SeparatorMenuItem());

		exitItem = new MenuItem("E_xit");
		exitItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		menu.getItems().add(exitItem);

		this.getMenus().add(menu);   

		menu = new Menu("_Help");

		aboutItem = new MenuItem("_About");
		aboutItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		menu.getItems().add(aboutItem);

		this.getMenus().add(menu); 
	}

	/**
	 * addSaveHandler
	 * @param handler - passed event handler class instantiation/reference
	 * Method adds the passed event handler to the saveItem menu bar item 
	 */
	public void addSaveHandler(EventHandler<ActionEvent> handler) {
		saveItem.setOnAction(handler);
	}
	
	/**
	 * addLoadHandler
	 * @param handler - passed event handler class instantiation/reference
	 * Method adds the passed event handler to the load item menu bar item
	 */
	public void addLoadHandler(EventHandler<ActionEvent> handler) {
		loadItem.setOnAction(handler);
	}
	
	/**
	 * addAboutHandler
	 * @param handler - passed event handler class instantiation/reference
	 * Method adds the passed event handler to the aboutItem menu bar item
	 */
	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		aboutItem.setOnAction(handler);
	}

	/**
	 * addExitHandler
	 * @param handler - passed event handler class instantiation/reference
	 * Method adds the passed event handler to the exitItem menu bar item
	 */
	public void addExitHandler(EventHandler<ActionEvent> handler) {
		exitItem.setOnAction(handler);
	}

}
