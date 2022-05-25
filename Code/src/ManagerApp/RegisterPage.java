package ManagerApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the Register page that gets launched right after the
 * user clicks on the reset button in the login page.
 * 
 * @author Chia-Shen Lin
 */
public class RegisterPage extends Application implements EventHandler<ActionEvent> {
	Text AskUser = new Text("Enter new username: ");
	TextField UserInput1 = new TextField();
	Text ConfirmUser = new Text("Confirm username: ");
	TextField UserInput2 = new TextField();
	Text AskPass = new Text("Enter new password: ");
	PasswordField PassInput1 = new PasswordField();
	Text ConfirmPass = new Text("Confirm password: ");
	PasswordField PassInput2 = new PasswordField();
	Button FinishButton = new Button("Finish");
	Alert alert = new Alert(AlertType.ERROR);
	Stage register = new Stage();

	/**
	 * Neatly arranges all the created buttons, text labels, and text fields on the
	 * register window and assign action events to the finish button
	 * 
	 * @param RStage register window
	 */
	public void start(Stage RStage) throws Exception {
		RStage.setTitle("Chillpass - Register/Reset Page");
		HBox h1 = new HBox();
		HBox h2 = new HBox();
		HBox h3 = new HBox();
		HBox h4 = new HBox();
		HBox h5 = new HBox();
		VBox vlayout = new VBox();
		register = RStage;

		h1.setSpacing(20);
		h1.setTranslateY(170);
		h2.setSpacing(20);
		h2.setTranslateY(170);
		h3.setSpacing(20);
		h3.setTranslateY(170);
		h4.setSpacing(20);
		h4.setTranslateY(170);
		h5.setSpacing(20);
		h5.setTranslateY(170);
		vlayout.setSpacing(20);
		h1.setAlignment(Pos.CENTER);
		h2.setAlignment(Pos.CENTER);
		h3.setAlignment(Pos.CENTER);
		h4.setAlignment(Pos.CENTER);
		h5.setAlignment(Pos.CENTER);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("username or password not confirmed successfully.");

		h1.getChildren().addAll(AskUser, UserInput1);
		h2.getChildren().addAll(ConfirmUser, UserInput2);
		h3.getChildren().addAll(AskPass, PassInput1);
		h4.getChildren().addAll(ConfirmPass, PassInput2);
		h5.getChildren().addAll(FinishButton);

		vlayout.getChildren().addAll(h1, h2, h3, h4, h5);

		Scene scene = new Scene(vlayout, 500, 500);
		RStage.setScene(scene);
		RStage.show();

		FinishButton.setOnAction(this::handle);
	}

	/**
	 * Brings the user back to the login page when user clicks on the finish button
	 * after entering the new master account.
	 * 
	 * 
	 */
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

		if (event.getSource() == FinishButton) {
			String newuser = UserInput1.getText();
			String conuser = UserInput2.getText();
			String newpass = PassInput1.getText();
			String conpass = PassInput2.getText();

			if (newuser.equals(conuser) && newpass.equals(conpass)) {
				Connection link;
				try {
					link = DriverManager.getConnection("jdbc:mysql://localhost:3306/chillpass", "admin", "admin");
					PreparedStatement pt = link.prepareStatement("Update masteraccount set password=? where UserID=?");
					pt.setString(1, conpass);
					pt.setInt(2, 1);
					pt.executeUpdate();
					PreparedStatement pt2 = link.prepareStatement("Update masteraccount set username=? where UserID=?");
					pt2.setString(1, conuser);
					pt2.setInt(2, 1);
					pt2.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				register.close();
				Stage c = new Stage();
				try {
					new LoginPage().start(c);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				alert.showAndWait();
				return;
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
