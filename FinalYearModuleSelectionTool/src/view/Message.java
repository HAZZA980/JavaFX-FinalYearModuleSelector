package view;

import javafx.scene.control.Alert;

/**
 * ErrorMessage
 * Simple class to quickly and easily display an error message onto the screen
 */
public class Message extends Alert{

    /**
     * ErrorMessage
     * @param title - the passed title for the message
     * @param header - the passed header for the message
     * @param body - the passed body for the message
     * Constructor accepts passed information and assigned it to the relevant fields within the message 
     * The created message is then shows and can be dismissed by the user.
     */
    public Message(String title,String header,String body,AlertType alert){
        super(alert);
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(body);
        this.show();
    }
}
