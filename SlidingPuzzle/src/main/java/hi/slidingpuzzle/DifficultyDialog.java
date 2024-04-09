package hi.slidingpuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import vinnsla.Difficulty;

import java.io.IOException;
import java.util.Optional;

public class DifficultyDialog extends Dialog<Difficulty> {

    Difficulty difficulty =new Difficulty(0);
    @FXML
    private ButtonType fxILagi;


    public DifficultyDialog(){
        setDialogPane(lesaDifficultyDialog());
        iLagiRegla();
        setResultConverter(b -> {
            if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                return difficulty;
            } else {
                return null;
            }
        });
    }

    private DialogPane lesaDifficultyDialog(){
        FXMLLoader fxmlLoader = new FXMLLoader((getClass()).getResource("Difficulty-view.fxml"));
        try{
            fxmlLoader.setController(this);
            return fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }
    private void iLagiRegla() {
        Node iLagi = getDialogPane().lookupButton(fxILagi);
        iLagi.setDisable(true);

    }
    public Difficulty getDifficultyValue(){
        Optional<Difficulty> utkoma = showAndWait();
        return utkoma.orElse(null);
    }
    public void easy(ActionEvent actionEvent){
        difficulty.setValue(3);
        Node iLagi = getDialogPane().lookupButton(fxILagi);
        iLagi.setDisable(false);
    }
    public void medium(ActionEvent actionEvent){
        difficulty.setValue(4);
        Node iLagi = getDialogPane().lookupButton(fxILagi);
        iLagi.setDisable(false);
    }
    public void hard(ActionEvent actionEvent){
        difficulty.setValue(5);
        Node iLagi = getDialogPane().lookupButton(fxILagi);
        iLagi.setDisable(false);
    }
}
