/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author hero
 * @param <T>
 */
public class AutoCompleteAPI implements EventHandler<KeyEvent> {

    private final ComboBox comboBox;
    private final StringBuilder sb;
    private ObservableList<String> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private final JSONDataSource dataSource;
    private long lastClickTime=0;
    private final Timer timer;
    public AutoCompleteAPI(final ComboBox comboBox,JSONDataSource dataSource) {
        this.comboBox = comboBox;
        this.dataSource = dataSource;
        sb = new StringBuilder();
        this.comboBox.setEditable(true);
        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if((System.currentTimeMillis()-lastClickTime)>1000)
                {
                    Platform.runLater(()->{
                        handle(new KeyEvent(null, "", "", KeyCode.A, false, false, false, false));
                    });
                }
            }
        }, 1000, 1000);
        this.comboBox.setOnKeyPressed((KeyEvent t) -> {
            comboBox.hide();
        });
        this.comboBox.setOnKeyReleased(AutoCompleteAPI.this);
    }

    @Override
    public void handle(KeyEvent event) {
        if(null != event.getCode()) switch (event.getCode()) {
            case UP:
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            case DOWN:
                if(!comboBox.isShowing()) {
                    comboBox.show();
                }
                caretPos = -1;
                moveCaret(comboBox.getEditor().getText().length());
                return;
            case BACK_SPACE:
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
                break;
            case DELETE:
                moveCaretToPos = true;
                caretPos = comboBox.getEditor().getCaretPosition();
                break;
            default:
                break;
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        if((System.currentTimeMillis()-lastClickTime)<1000)
        {
            lastClickTime = System.currentTimeMillis();
            return;
        }
        lastClickTime = System.currentTimeMillis();
        String t = comboBox.getEditor().getText();
        if(comboBox.getItems().contains(t)) return ;
        comboBox.setItems(FXCollections.observableArrayList(dataSource.fetchResult(comboBox.getEditor().getText())));
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!comboBox.getItems().isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }
    
}
