import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	private Button startBut;
	private Button quitBut;
	private Button playAgainBut;
	private MenuBar mainMenu;
	private GridPane grid;
	private Text welcomeMsg;
	private int totalMoves = 0;
	private BorderPane bpane;
	private Insets topInsets;
	private Scene scene1, scene2;
    GameButton gb;
    EventHandler<ActionEvent> myHandler;
    Dialog<String> instructions;
    ObservableList<String> moves;
    ListView<String> listView;
    Text playerTurn;
    Stack<GameButton> stack = new Stack<GameButton>();
    Set<GameButton> set = new HashSet<GameButton>();
	private List<GameButton> list = new ArrayList<>();
	private Text resultText;
	PauseTransition pause = new PauseTransition(Duration.seconds(2));

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Connect Four!");
		
		//welcome text node with changed font
		welcomeMsg = new Text("Welcome Connect4 Players!");
//		welcomeMsg.setFill(Color.LIGHTBLUE);
		welcomeMsg.setFont(Font.font ("Optima", 40));
		
		//large transparent button with red background
		startBut = new Button("CLICK ANYWHERE TO START");
		startBut.setBackground(null);
		startBut.setStyle("-fx-background-color: red;");
		startBut.setMinSize(500, 500);
		startBut.setMaxSize(1500, 800);
		startBut.setPrefSize(600, 750);
		
		//padding for the welcome message
		topInsets = new Insets(50, 100, 50, 100);
		
		//borderPane holds welcome message up top and start button in center(large clickable area)
		bpane = new BorderPane();
		bpane.setTop(welcomeMsg);
		bpane.setCenter(startBut);
		BorderPane.setMargin(welcomeMsg, topInsets);
		
		//menubar for scene1 with 3 menus that have menu options
		mainMenu = new MenuBar();
		Menu gamePlay = new Menu("Gameplay");
		Menu themes = new Menu("Themes");
		Menu options = new Menu("Options");
		MenuItem g1 = new MenuItem("Reverse Move");
		MenuItem t1 = new MenuItem("Theme 1");
		MenuItem t2 = new MenuItem("Theme 2");
		MenuItem o1 = new MenuItem("How To Play");
		MenuItem o2 = new MenuItem("New Game");
		MenuItem o3 = new MenuItem("Exit");
		gamePlay.getItems().add(g1);
		themes.getItems().add(t1);
		themes.getItems().add(t2);
		options.getItems().add(o1);
		options.getItems().add(o2);
		options.getItems().add(o3);
		mainMenu.getMenus().addAll(gamePlay, themes, options);
		
		playerTurn = new Text();
		playerTurn.setText("Red's Turn");
		playerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		//event handler for button pressed in connect4
		myHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				gb = (GameButton)e.getSource();
				if (isValid(gb, grid)) {
					
					gb.setDisable(true);

					totalMoves++;

					if(totalMoves % 2 == 1) {
						gb.setStyle("-fx-background-color: red;");
						gb.setColor("Red");
						moves.add("Valid Move: Red Moved to " + "(" + gb.getRow() +"," + gb.getCol() +")");
						playerTurn.setText("Blue's Turn");
					}
					else {
						gb.setStyle("-fx-background-color: lightblue;");
						gb.setColor("Blue");
						moves.add("Valid Move: Blue Moved to " + "(" + gb.getRow() +"," + gb.getCol() +")");
						playerTurn.setText("Red's Turn");
					}
					
					if(isWin(grid, gb)) {
						
						if(gb.getColor() == "Red" ) {
							resultText.setText("Red is the Winner!");
						}
						else if(gb.getColor() == "Blue" ) {
							resultText.setText("Red is the Winner!");
						}
						
						pause.play();
						
						pause.setOnFinished(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								
								disableAll();
								System.out.println("WINNER!!");
								primaryStage.setScene(scene2);
								primaryStage.show();
								
							}
						});
						
					}
					
					if(totalMoves == 42) {
						
						if(totalMoves == 42) {
							resultText.setText("Tie Game!");
						}
						
						pause.play();
						
						pause.setOnFinished(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								
								primaryStage.setScene(scene2);
								scene2.getRoot().setStyle("-fx-font-family: 'Optima'");
								primaryStage.show();
								
							}
						});
						
					}
					
					stack.push(gb);
					
				}
				else {
					
					if(totalMoves % 2 == 1) {
						gb.setColor("Blue");
					}
					else {
						gb.setColor("Red");
					}
					
					moves.add("Invalid Move: " + gb.getColor() + " tried moving to " + "(" + gb.getRow() +"," + gb.getCol() +")" +". Try Again.");
					
				}
			}
		};
		
		//customize layout of grid and call makegrid function to make grid
		grid = new GridPane();
		grid.setAlignment(Pos.BOTTOM_CENTER);
		grid.setHgap(2.5);									  //horizontal padding for individual buttons
        grid.setVgap(2.5);									  //vertical padding for individual buttons
        grid.setPadding(new Insets(60, 60, 60, 60));      //padding for entire grid
        makeGrid(grid);
		
        //display instructions in dialog box via click of a button
        Dialog<String> instructions = new Dialog<String>();
		instructions.setTitle("Instructions");
		instructions.setContentText("Connect Four is played on a grid of 7 columns and 6 rows.\n"
				+ "It is a two player game where each player takes a turn dropping a checker into a slot (one of the columns) on the game board.\n"
				+ "That checker will fall down the column and either land on top of another checker or land on the bottom row."
				+ "To win the game, a player needs to get 4 of their checkers in a vertical, horizontal or diagonal row before the other player.");
        
		//Adding buttons to the dialog pane
		ButtonType type = new ButtonType("Close", ButtonData.OK_DONE);
        instructions.getDialogPane().getButtonTypes().add(type);
        
        g1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				reverseMove();
			}
		});
        
        //menu option to display instructions
        o1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instructions.showAndWait();
			}
		});
        
		//menu option to exit
		o3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//menu option to start a new game
		o2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				resetGame(grid);
			}
		});
		
		moves = FXCollections.observableArrayList();
		listView = new ListView<String>(moves);
		listView.prefHeight(50);
		listView.prefWidth(80);
		
		BorderPane bpane1 = new BorderPane();
		HBox hbox = new HBox(playerTurn, listView);
		hbox.setPadding(new Insets(25, 50, 25, 50));
		hbox.setSpacing(100);
		bpane1.setTop(mainMenu);
		bpane1.setBottom(grid);
		bpane1.setCenter(hbox);
		
		scene1 = new Scene(new VBox(bpane1), 700, 700);
		
		quitBut = new Button("Quit");
		quitBut.setPrefSize(200, 100);
		playAgainBut = new Button("Play Again");
		playAgainBut.setPrefSize(200, 100);
		HBox endButtons = new HBox(quitBut, playAgainBut);
		BorderPane endButPane = new BorderPane();
		endButtons.setSpacing(200);
		endButtons.setPadding(new Insets(75, 75, 75, 75));
		endButPane.setBottom(endButtons);
		resultText = new Text();
		
		endButPane.setCenter(resultText);
		
		scene2 = new Scene(endButPane, 700, 700);
		
//		endButPane.setPadding(new Insets(50, 50, 50, 50));
		
		quitBut.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		playAgainBut.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(scene1);
				scene1.getRoot().setStyle("-fx-font-family: 'Optima'");
				primaryStage.show();
				resetGame(grid);
			}
		});
		
		t1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                mainMenu.setStyle("-fx-background-color: orange;" + "-fx-font-family: Onyx;" + "-fx-font-size: 30");
                bpane1.setStyle("-fx-background-color: darkgrey;");
                playerTurn.setStyle("-fx-text-fill: lightblue");

            }
        });
		
		t2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				ImageView iM1 = new ImageView();
				ImageView iM2 = new ImageView();
				iM1.setImage(new Image("pic.jpg"));
				iM1.setFitHeight(200);
				iM1.setFitWidth(100);
				iM1.setPreserveRatio(true);
				bpane1.setRight(iM1);
				iM2.setImage(new Image("pic2.jpg"));
				iM2.setFitHeight(200);
				iM2.setFitWidth(100);
				iM2.setPreserveRatio(true);
				bpane1.setLeft(iM2);
				bpane1.setStyle("-fx-background-color: lightgreen;");
			}
		});
		
		
		//when start button clicked, switch to next scene with grid to play connect4
		startBut.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
//				System.out.println("Button Pressed");
				primaryStage.setScene(scene1);
				scene1.getRoot().setStyle("-fx-font-family: 'Optima'");
			}
		});
		
		Scene scene = new Scene(new VBox(bpane), 700, 700);
		scene.getRoot().setStyle("-fx-font-family: 'Optima'");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void reverseMove() {
		
		if(stack.isEmpty()) {
			System.out.println("Error: Can't undo. Move has not been made.");
		}
		else {
			
			if(gb.getColor() == "Blue") {
				playerTurn.setText("Blue's Turn");
			}
			else {
				playerTurn.setText("Red's Turn");
			}
			stack.peek().setColor(null);
			stack.peek().setStyle ("I: want to restore the default color of JavaFX");
			stack.peek().setDisable(false);
			stack.pop();
			moves.add("Last Move Reversed");
			
			totalMoves--;
			
		}
		
	}
	
	public boolean isValid(GameButton b1, GridPane grid) {

		//if not bottom row
		if(b1.getRow() != 5) {
			GameButton b2 = (GameButton) getNodeFromGridPane(grid, b1.getCol(), b1.getRow() + 1);

			if(!b2.isDisabled()) {  //if there is NOT a piece underneath then not valid move
				return false;
			}
		}
		return true;
	}
	
	public void disableAll() {
		for(Node node: grid.getChildren()) {
			node.setDisable(true);
		}
	}

	public boolean isWin(GridPane grid, GameButton b1) {

		if(isWinVertical(b1)) { return true; }

		if(isWinHorizontal(b1)) { return true; }

		if(isWinDiagonal(b1)) { return true; }

		return false;
	}

	private boolean isWinVertical(GameButton b1) {
		String color = b1.getColor();
		GameButton cur = b1;
		GameButton cur2 = b1;
		int count = 0;

		while(cur.getColor() == color) {
			count++;
			set.add(cur);
			if(cur.getRow() == 5)
				break;
			cur = getNodeFromGridPane(grid, cur.getCol(), cur.getRow() + 1);
		}
		if(count >= 4) {
			list.addAll(set);

			for(GameButton b2: list) {
				b2.setStyle("-fx-border-color: gold");
			}
			list.clear();
			set.clear();
			return true;
		}
		list.clear();
		set.clear();

		return false;
	}

	private boolean isWinHorizontal(GameButton b1) {
		String color = b1.getColor();
		GameButton cur = b1;
		GameButton cur2 = b1;
		int count = 0;

			while(cur.getColor() == color) {
				count++;
				set.add(cur);
				if(cur.getCol() == 6)
					break;
				cur = getNodeFromGridPane(grid, cur.getCol() + 1, cur.getRow());
			}
			count--;
			while(cur2.getColor() == color) {
				count++;
				set.add(cur2);
				if(cur2.getCol() == 0)
					break;
				cur2 = getNodeFromGridPane(grid, cur2.getCol() - 1, cur2.getRow());
			}
			//System.out.println(count);
			if(count >= 4) {
				list.addAll(set);

				for(GameButton b2: list) {
					b2.setStyle("-fx-border-color: gold");
				}
				list.clear();
				set.clear();
				return true;
			}
			list.clear();
			set.clear();

		return false;
	}

	private boolean isWinDiagonal(GameButton b1) {
		String color = b1.getColor();
		GameButton cur = b1;
		GameButton cur2 = b1;
		int count = 0;

		//check for diagonal that goes up and to the right
		while(cur.getColor() == color) {
			count++;
			set.add(cur);
			if(cur.getRow() == 0 || cur.getCol() == 6)
				break;
			cur = getNodeFromGridPane(grid, cur.getCol() + 1, cur.getRow() - 1);
		}
		count--;
		while(cur2.getColor() == color) {
			count++;
			set.add(cur2);
			if(cur2.getRow() == 5 || cur2.getCol() == 0)
				break;
			cur2 = getNodeFromGridPane(grid, cur2.getCol() - 1, cur2.getRow() + 1);
		}

		if(count >= 4) {
			list.addAll(set);

			for(GameButton b2: list) {
				b2.setStyle("-fx-border-color: gold");
			}
			list.clear();
			set.clear();
			return true;
		}

		//reset variables
		color = b1.getColor();
		cur = b1;
		cur2 = b1;
		count = 0;
		set.clear();

		//check for diagonal that goes to the right
		while(cur.getColor() == color) {
			count++;
			set.add(cur);
			if(cur.getRow() == 0 || cur.getCol() == 0)
				break;
			cur = getNodeFromGridPane(grid, cur.getCol() - 1, cur.getRow() - 1);
		}
		count--;
		while(cur2.getColor() == color) {
			count++;
			set.add(cur2);
			if(cur2.getRow() == 5 || cur2.getCol() == 6)
				break;
			cur2 = getNodeFromGridPane(grid, cur2.getCol() + 1, cur2.getRow() + 1);
		}
		if(count >= 4) {
			list.addAll(set);

			for(GameButton b2: list) {
				b2.setStyle("-fx-border-color: gold");
			}
			list.clear();
			set.clear();
			return true;
		}
		list.clear();
		set.clear();
		return false;
	}

	private GameButton getNodeFromGridPane(GridPane grid, int col, int row) {

		ObservableList<Node> children = grid.getChildren();
		for (Node node : children) {
			Integer columnIndex = grid.getColumnIndex(node);
			Integer rowIndex = grid.getRowIndex(node);

			if (columnIndex == null)
				columnIndex = 0;
			if (rowIndex == null)
				rowIndex = 0;

			if (columnIndex == col && rowIndex == row) {
				return (GameButton) node;
			}
		}
		return null;
	}
	
	public void resetGame(GridPane grid){
		
		for(Node n: grid.getChildren()) {
			gb = (GameButton) n;
			gb.setDisable(false);
			gb.setStyle ("I: want to restore the default color of JavaFX");
			gb.setColor("lightgrey");
		}
		
		playerTurn.setText("Red's Turn");
		moves.clear();
		totalMoves = 0;
		
	}
	
  	public void makeGrid(GridPane grid) {
		
		for(int i = 0; i < 7; i++) {
			
			for(int j = 0; j < 6; j++) {
				
				gb = new GameButton();
				gb.setRow(j);
				gb.setCol(i);
				gb.setMinSize(60,60);
				gb.setMaxSize(70,70);
				gb.setPrefSize(65,65);
				gb.setColor("lightgrey");
				gb.setOnAction(myHandler);
				grid.add(gb, i, j);
				
				
			}
			
		}
		
	}

}
