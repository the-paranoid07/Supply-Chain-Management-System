package com.example.supplychianmanagementsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;//this is the necessary import for Pane
import javafx.stage.Stage;


import java.io.IOException;

public class SupplyChain extends Application {
    LoginVerify login=new LoginVerify();
    ProductDetails productDetails=new ProductDetails();
    //Label messageLabel,passwordLabel,emailLabel;

    public static final int width=700,height=600,headerBar=50;

    Pane bodyPane=new Pane();

    Button globalLoginButton;
    Label customerEmailLabel=null;
    String customerEmail=null;

    private GridPane headerBar(){
        TextField searchText=new TextField();
        Button searchButton=new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName=searchText.getText();
                productDetails.getProductsByName(productName);

                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });
        globalLoginButton=new Button("Log In");
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
                globalLoginButton.setDisable(true);

            }
        });

        customerEmailLabel=new Label("Welcome User!");

        GridPane gridPane=new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),headerBar-10);

        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(searchText,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(globalLoginButton,2,0);
        gridPane.add(customerEmailLabel,3,0);

        return gridPane;
    }

    private GridPane loginPage(){

       Label emailLabel=new Label("Email :");
        Label passwordLabel=new Label("Password :");
        Label messageLabel=new Label("I am message");

        TextField emailTextField=new TextField();
        PasswordField passwordTextField=new PasswordField();

        Button loginButton=new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email=emailTextField.getText();
                String password=passwordTextField.getText();
               // messageLabel.setText(email+"$$"+password);
                if(login.customerLogin(email,password)){
                    messageLabel.setText("Login success");
                    customerEmail=email;
                    globalLoginButton.setDisable(true);
                    customerEmailLabel.setText("Welcome : "+customerEmail);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());
                }else messageLabel.setText("Login failed");

            }
        });
        GridPane gridPane=new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),bodyPane.getMinHeight());

        gridPane.setVgap(5);
        gridPane.setHgap(5);
       // gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(emailLabel,0,0);
        gridPane.add(emailTextField,1,0);
        gridPane.add(passwordLabel,0,1);
        gridPane.add(passwordTextField,1,1);
        gridPane.add(loginButton,0,2);
        gridPane.add(messageLabel,1,2);

        return gridPane;

    }

    private GridPane footerBar(){
        Label messageLabel=new Label("");

        Button addToCartButton=new Button("Add To Cart");
        Button buyNowButton=new Button("Buy Now");

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct=productDetails.getSelectedProduct();
                if(Order.placeOrder(customerEmail,selectedProduct)){
                    messageLabel.setText("Order Placed");
                }
                else{
                    messageLabel.setText("Order Failed");
                }
            }
        });

        GridPane gridPane=new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),headerBar-10);

        gridPane.setVgap(5);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setTranslateY(headerBar+height+5);

        gridPane.add(addToCartButton,0,0);
        gridPane.add(buyNowButton,1,0);
        gridPane.add(messageLabel,2,0);


        return gridPane;
    }


    private Pane createContent(){
        Pane root=new Pane();

        root.setPrefSize(width,height+2*headerBar);

        bodyPane.setMinSize(width,height);
        bodyPane.setTranslateY(headerBar);

        //bodyPane.getChildren().addAll(loginPage());
        bodyPane.getChildren().addAll(productDetails.getAllProducts());
        root.getChildren().addAll(headerBar(),bodyPane,footerBar());
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("SUPPLY CHAIN MANAGEMENT SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}