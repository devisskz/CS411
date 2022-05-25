package ManagerApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewCredentials extends Application implements EventHandler<ActionEvent> {

	String website = "";
	String username = "";
	String password = "";
	Text UserLabel = new Text("Welcome " + MasterAccount.username + "!");
	Text UserID = new Text("User ID: " + MasterAccount.ID);
	Text Intro = new Text("Below are your stored credentials: ");
	public TableView<Credential> table = new TableView<Credential>();
	// public ObservableList<Credential> collection;
	Button AddButton = new Button("+");
	Button DButton = new Button("Delete");
	TextField addService = new TextField();
	TextField addUser = new TextField();
	TextField addPass = new TextField();

	Stage View = new Stage();

	public void start(Stage secondaryStage) throws Exception {

		secondaryStage.setTitle(MasterAccount.username + "'s credentials");

		table.setEditable(true);
		TableColumn ServiceCol = new TableColumn("Website/Service Name");
		ServiceCol.setMinWidth(150);
		ServiceCol.setCellValueFactory(new PropertyValueFactory<>("Servicename"));
		ServiceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ServiceCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
			@Override
			public void handle(CellEditEvent event) {
				event.getNewValue();
			}
		});

		TableColumn UserCol = new TableColumn("Username");
		UserCol.setMinWidth(170);
		UserCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
		UserCol.setCellFactory(TextFieldTableCell.forTableColumn());
		UserCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
			@Override
			public void handle(CellEditEvent event) {
				event.getNewValue();
			}
		});

		TableColumn PassCol = new TableColumn("Password");
		PassCol.setMinWidth(170);
		PassCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
		PassCol.setCellFactory(TextFieldTableCell.forTableColumn());
		PassCol.setOnEditCommit(new EventHandler<CellEditEvent>() {
			@Override
			public void handle(CellEditEvent event) {
				event.getNewValue();
			}
		});

		// table.setItems(collection);
		addService.setPromptText("Website/Service Name");
		// addService.setMaxWidth(ServiceCol.getPrefWidth());
		addService.setMinWidth(100);
		// addUser.setMaxWidth(UserCol.getPrefWidth());
		addUser.setMinWidth(100);
		addUser.setPromptText("Username");
		// addPass.setMaxWidth(PassCol.getPrefWidth());
		addPass.setMinWidth(100);
		addPass.setPromptText("Password");
		table.getColumns().addAll(ServiceCol, UserCol, PassCol);

		Class.forName("java.sql.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chillpass", "admin", "admin");
		Statement stmt = con.createStatement();
		String query = "select * from pass";
		ResultSet rs = stmt.executeQuery(query);
		// DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
		while (rs.next()) {
			website = rs.getString("website");
			username = rs.getString("username");
			password = rs.getString("password");
			// table.addRow(new Object[] {website, username, password});
			table.getItems().add(new Credential(website, username, password));
		}

		VBox vlayout = new VBox();
		HBox h1 = new HBox();
		vlayout.setSpacing(5);
		vlayout.setAlignment(Pos.TOP_LEFT);
		AddButton.setShape(new Circle(1.5));
		AddButton.setTranslateY(60);
		AddButton.setTranslateX(235);
		DButton.setShape(new Circle(1.5));
		DButton.setTranslateY(70);
		DButton.setTranslateX(223);
		h1.getChildren().addAll(addService, addUser, addPass);
		h1.setSpacing(5);
		h1.setAlignment(Pos.CENTER);
		h1.setTranslateY(50);
		vlayout.getChildren().addAll(UserLabel, UserID, Intro, table, AddButton, DButton);
		vlayout.setPadding(new Insets(0, 10, 0, 10));
		Scene scene = new Scene(vlayout, 500, 600);
		secondaryStage.setScene(scene);
		View = secondaryStage;
		secondaryStage.show();

		AddButton.setOnAction(this::handle);
		DButton.setOnAction(this::handle);

	}

	public void handle1(CellEditEvent event) {
		// TODO Auto-generated method stub
		event.getNewValue();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == AddButton) {
//			table.getItems().add(new Credential(addService.getText(), addUser.getText(), addPass.getText()));
//			addService.clear();
//			addUser.clear();
//			addPass.clear();
			View.close();
			Stage c = new Stage();
			try {
				new AddCredentials().start(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (event.getSource() == DButton) {
			View.close();
			Stage c = new Stage();
			try {
				new DeleteCredentials().start(c);
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
