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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeleteCredentials extends Application implements EventHandler<ActionEvent> {

	Text AskDelete = new Text("Website/service name to delete: ");
	TextField DeleteInput = new TextField();
	Button FinishButton = new Button("Confirm");

	Stage D = new Stage();

	public void start(Stage DStage) throws Exception {
		// TODO Auto-generated method stub
		DStage.setTitle("Chillpass - Delete credentials");
		HBox h1 = new HBox();
		D = DStage;

		h1.setSpacing(30);
		// h1.setTranslateY(100);
		h1.setAlignment(Pos.CENTER);

		h1.getChildren().addAll(AskDelete, DeleteInput, FinishButton);

		Scene scene = new Scene(h1, 500, 500);
		DStage.setScene(scene);
		DStage.show();
		FinishButton.setOnAction(this::handle);
	}

	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == FinishButton) {
			try {
				Class.forName("java.sql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection con;
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chillpass", "admin", "admin");
				// Statement delete = con.createStatement();
				// String s = "delete from pass" + "where website = ?";
				String Del = DeleteInput.getText();
				PreparedStatement st = con.prepareStatement("DELETE FROM pass WHERE website = ?");
				st.setString(1, Del);
				st.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			D.close();
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
