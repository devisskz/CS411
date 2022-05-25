package ManagerApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddCredentials extends Application implements EventHandler<ActionEvent> {
	Text AskService = new Text("Enter website/service name: ");
	TextField ServiceInput = new TextField();
	Text AskUser = new Text("Enter username: ");
	TextField UserInput = new TextField();
	Text AskPass = new Text("Enter password: ");
	TextField PassInput = new PasswordField();
	Button FinishButton = new Button("Complete");
	Stage addition = new Stage();

	@Override
	public void start(Stage AStage) throws Exception {
		// TODO Auto-generated method stub
		AStage.setTitle("Chillpass - Add credentials");
		HBox h1 = new HBox();
		HBox h2 = new HBox();
		HBox h3 = new HBox();
		HBox h4 = new HBox();
		VBox vlayout = new VBox();
		addition = AStage;

		h1.setSpacing(30);
		h1.setTranslateY(170);
		h2.setSpacing(30);
		h2.setTranslateY(170);
		h3.setSpacing(30);
		h3.setTranslateY(170);
		h4.setSpacing(30);
		h4.setTranslateY(170);

		vlayout.setSpacing(40);
		h1.setAlignment(Pos.CENTER);
		h2.setAlignment(Pos.CENTER);
		h3.setAlignment(Pos.CENTER);
		h4.setAlignment(Pos.CENTER);

		h1.getChildren().addAll(AskService, ServiceInput);
		h2.getChildren().addAll(AskUser, UserInput);
		h3.getChildren().addAll(AskPass, PassInput);
		h4.getChildren().addAll(FinishButton);

		vlayout.getChildren().addAll(h1, h2, h3, h4);

		Scene scene = new Scene(vlayout, 500, 500);
		AStage.setScene(scene);
		AStage.show();

		FinishButton.setOnAction(this::handle);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == FinishButton) {
			Connection add;
			try {
				String website = ServiceInput.getText();
				String username = UserInput.getText();
				String password = PassInput.getText();
				add = DriverManager.getConnection("jdbc:mysql://localhost:3306/chillpass", "admin", "admin");
				Statement st = add.createStatement();
				String q = "INSERT INTO pass (website, username, password)" + "VALUES (?, ?, ?)";
				PreparedStatement preparedStatement = add.prepareStatement(q);
				preparedStatement.setString(1, website);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, password);
				preparedStatement.executeUpdate();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			addition.close();
			Stage c = new Stage();
			try {
				new ViewCredentials().start(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
