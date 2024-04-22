package controllers;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class Router {

    private static Router instance ;
    private HBox Currentcontent ;


    private Router() {
    }

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public void setContent(HBox content) {
        this.Currentcontent = content;
    }

    public HBox getContent() {
        return this.Currentcontent;
    }
    public void showContent(Parent content) {
        this.Currentcontent.getChildren().clear();
        this.Currentcontent.getChildren().add(content);
    }

}
