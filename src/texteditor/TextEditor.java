/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Mohab
 */
public class TextEditor extends Application {
    MenuBar bar;
    Menu file,edit,help;
    MenuItem New,open,save,exit,undo,cut,past,copy,delete,selectAll,about;
    TextArea area;
    Clipboard systemClipboard = Clipboard.getSystemClipboard();
    String copyText;
    ClipboardContent content;
    SeparatorMenuItem sep,sep1,sep2;
    @Override
    public void start(Stage primaryStage) {
        bar = new MenuBar();
        file = new Menu("File");
        edit = new Menu("Edit");
        help = new Menu("Help");
        
        //file buttons
        New = new MenuItem("New");
        New.setAccelerator(KeyCombination.keyCombination("ctrl+n"));
        
        open = new MenuItem("Open");
        New.setAccelerator(KeyCombination.keyCombination("ctrl+o"));
        
        save = new MenuItem("Save");
        exit = new MenuItem("Exit");
        //edit buttons
        undo = new MenuItem("Undo");
        cut = new MenuItem("Cut");
        copy = new MenuItem("Copy");
        past = new MenuItem("Past");
        delete = new MenuItem("Delete");
        selectAll = new MenuItem("SelectAll");
        //Help button
        about = new MenuItem("About/Help");
        //create seprator
        sep = new SeparatorMenuItem();
        sep1 = new SeparatorMenuItem();
        sep2 = new SeparatorMenuItem();
        //Actions
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                showSingleFileChooser();
                 
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                FileChooser fileChooser = new FileChooser();
 
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
 
            //Show save file dialog
            File file = fileChooser.showSaveDialog(primaryStage);
 
            if (file != null) {
                saveTextToFile(area.getText(), file);
            }
            }
        });
        New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(area.getText() != "")
                {
                System.out.print(area.getText());
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Warning !");
                alert.setHeaderText("Data will be deleted");
                alert.setContentText("Are you Sure?");
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    //clear area
                    System.out.print("clicked ok");   
                    area.clear();
                }else{
                    //don't do anything
                    System.out.print("canceled");            
                }
                }
                
            }
        });
        //exit
        //=========================================================
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                copyText = area.getSelectedText();
                content = new ClipboardContent();
                content.putString(copyText);
                systemClipboard.setContent(content);
            }
        });
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                copyText = area.getSelectedText();
                System.out.print(copyText);
                content = new ClipboardContent();
                content.putString(copyText);
                systemClipboard.setContent(content);    
                area.replaceSelection("");      
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                area.setText(area.getText().replace(area.getSelectedText(),""));
            }
        });
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                area.selectAll();
            }
        });
        past.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(copyText != null)
                {
                    area.insertText(area.getCaretPosition(), copyText);
                }
            }
        });
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                area.undo();
            }
        });
        //=========================================================
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Help/About");
                alert.setHeaderText(null);
                alert.setContentText("This application made by Mohab Rabie :D ");
                alert.showAndWait();
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                primaryStage.close();
            }
        });
        //add items
        file.getItems().addAll(New,open,save,exit);
        file.getItems().add(3,sep);
        edit.getItems().addAll(undo,cut,copy,past,delete,selectAll);
        edit.getItems().add(1,sep1);
        edit.getItems().add(6,sep2);
                
        help.getItems().addAll(about);
        
        bar.getMenus().addAll(file,edit,help);
        
        area = new TextArea();
        
        BorderPane pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(area);
        //root.getChildren().add();
        
        Scene scene = new Scene(pane, 500, 500);
        
        
        
        
        
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    private void showSingleFileChooser() {

    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(null);
    
    if (selectedFile != null) {
        System.out.println(selectedFile);
        //area.setText();
        try (Scanner scanner = new Scanner(new File(selectedFile.toString()))) {

        while (scanner.hasNextLine())
            area.appendText(scanner.nextLine());
            area.appendText("\n");
        //(scanner.next() + " ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   }
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
/*

 FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);
                filePath = file;
                if (file != null) {

                    try {
                        FileInputStream fis = new FileInputStream(file);
                        byte b[] = new byte[(int) file.length()];
                        fis.read(b);
                        String data = new String(b);

                        np.textArea.setText(data);
                        txt = np.textArea.getText();

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NotePad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

*/