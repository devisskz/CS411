package ManagerApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
// import javafx.scene.paint.Color; 
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
 * Class that represents the login page that gets launched right after the user
 * starts the Chillpass application
 * 
 * @author Chia-Shen Lin
 */
public class LoginPage extends Application implements EventHandler<ActionEvent> {

	Text UserLabel = new Text("Username: ");
	TextField UserInput = new TextField();
	Text PassLabel = new Text("Password: ");
	PasswordField PassInput = new PasswordField();
	Button LoginButton = new Button("Login");
	// Button CButton = new Button("Reset Account");
	Button RButton = new Button("Register/Reset account");
	Alert alert = new Alert(AlertType.ERROR);
	Alert success = new Alert(AlertType.INFORMATION);
	Stage first = new Stage();

	/**
	 * Neatly arranges all the created buttons, text labels, and text fields on the
	 * login window and assign action events to the login and reset buttons
	 * 
	 * @param primaryStage login window
	 */
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Chillpass - No.1 Password Manager");
		HBox h1 = new HBox();
		HBox h2 = new HBox();
		HBox h3 = new HBox();
		VBox vlayout = new VBox();
		first = primaryStage;

		h1.setSpacing(20);
		h1.setTranslateY(170);
		h2.setSpacing(20);
		h2.setTranslateY(170);
		h3.setSpacing(20);
		h3.setTranslateY(170);
		vlayout.setSpacing(20);
		h1.setAlignment(Pos.CENTER);
		h2.setAlignment(Pos.CENTER);
		h3.setAlignment(Pos.CENTER);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Incorrect username or password");
		success.setTitle(null);
		success.setHeaderText(null);
		success.setContentText("Login succeeded!");

		h1.getChildren().addAll(UserLabel, UserInput);
		h2.getChildren().addAll(PassLabel, PassInput);
		h3.getChildren().addAll(LoginButton, RButton);

		vlayout.getChildren().addAll(h1, h2, h3);

		Scene scene = new Scene(vlayout, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

		LoginButton.setOnAction(this::handle);
		RButton.setOnAction(this::handle);

	}

	/**
	 * Pulls up the view credentials/register page when user clicks on the
	 * login/reset button after entering the correct master account.
	 * 
	 * 
	 */
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		int MasterID = 0;
		String Masteruser = "";
		String Masterpass = "";
		if (event.getSource() == LoginButton || event.getSource() == RButton) {
			try {
				Connection MasterLogin = DriverManager.getConnection("jdbc:mysql://localhost:3306/chillpass", "admin",
						"admin");
//				PreparedStatement st = MasterLogin.prepareStatement(
//						"Select username, password from masteraccount where username=? and password=?");
//				st.setString(1, Masteruser);
//				st.setString(2, Masterpass);
				Statement st = MasterLogin.createStatement();
				String q = "SELECT USERID, USERNAME, PASSWORD FROM MASTERACCOUNT";
				ResultSet rs = st.executeQuery(q);
				rs.next();
				MasterID = rs.getInt(1);
				Masteruser = rs.getString(2);
				Masterpass = rs.getString(3);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String username = UserInput.getText();
			String password = PassInput.getText();
			if (!username.equals(Masteruser) || !password.equals(Masterpass)) {
				alert.showAndWait();
				return;
			} else {
				if (event.getSource() == LoginButton) {
					MasterAccount.ID = MasterID;
					MasterAccount.username = Masteruser;
					MasterAccount.password = Masterpass;
					first.close();
					Stage c = new Stage();
					try {
						new ViewCredentials().start(c);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getSource() == RButton) {
					first.close();
					Stage c = new Stage();
					try {
						new RegisterPage().start(c);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Application.launch(CredentialsPage.class);
				// success.showAndWait();
				// return;
			}
		}

//		} else if (event.getSource() == RButton) {
//			
//			String username = UserInput.getText();
//			String password = PassInput.getText();
//			if (!username.equals(Masteruser) || !password.equals(Masterpass)) {
//				alert.showAndWait();
//				return;
//			} else {
//				first.close();
//				Stage c = new Stage();
//				try {
//					new RegisterPage().start(c);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}

	}

	/**
	 * Launch login window
	 * 
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
