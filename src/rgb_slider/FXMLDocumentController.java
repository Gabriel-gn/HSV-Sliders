/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgb_slider;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gabriel Nogueira
 */
public class FXMLDocumentController implements Initializable {

    int sliderValue = 0;
    int r = 0;
    int g = 0;
    int b = 0;

    int h = 0;
    int s = 0;
    int v = 0;

    @FXML
    private Button button;
    @FXML
    private AnchorPane pane_color;
    @FXML
    private Label labelR;
    @FXML
    private Label labelG;
    @FXML
    private Label labelB;
    @FXML
    private Slider sliderH;
    @FXML
    private Slider sliderS;
    @FXML
    private Slider sliderV;
    @FXML
    private Label labelH;
    @FXML
    private Label labelS;
    @FXML
    private Label labelV;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        int round = Math.round((int)(sliderV.getValue() * 100) / 20);
        switch (round) {
            case 0:
                sliderV.setValue(0.2);
                break;
            case 1:
                sliderV.setValue(0.4);
                break;
            case 2:
                sliderV.setValue(0.6);
                break;
            case 3:
                sliderV.setValue(0.8);
                break;
            case 4:
                sliderV.setValue(1.0);
                break;
            case 5:
                sliderV.setValue(0.0);
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderH.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateColor();
            labelH.setText("" + Math.round((double) newValue * 360));
        });

        sliderS.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateColor();
            labelS.setText("" + Math.round((double) newValue * 100));
        });

        sliderV.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateColor();
            labelV.setText("" + Math.round((double) newValue * 100));
        });
    }

    private void updateColor() {
        int[] retorno = hsvToRgb(sliderH.getValue(), sliderS.getValue(), sliderV.getValue());
        String hex = String.format("#%02x%02x%02x", retorno[0], retorno[1], retorno[2]);
        pane_color.setStyle("-fx-background-color: " + hex + "; -fx-background-radius: 500;");
        labelR.setText("" + retorno[0]);
        labelG.setText("" + retorno[1]);
        labelB.setText("" + retorno[2]);
    }

    private String toHex(int decimal) {
        int rem;
        String hex = "";
        char hexchars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (decimal > 0) {
            rem = decimal % 16;
            hex = hexchars[rem] + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    private int[] hsvToRgb(double H, double S, double V) {
        //H, S and V input range = 0 ÷ 1.0
        //R, G and B output range = 0 ÷ 255

        double R = 0;
        double G = 0;
        double B = 0;

        if (S == 0) {
            R = V * 255;
            G = V * 255;
            B = V * 255;
        } else {
            double var_h = H * 6;
            if (var_h == 6) {
                var_h = 0; //H must be < 1

            }

            double var_i = (int) (var_h);
            double var_1 = V * (1 - S);
            double var_2 = V * (1 - S * (var_h - var_i));
            double var_3 = V * (1 - S * (1 - (var_h - var_i)));

            double var_r = 0;
            double var_g = 0;
            double var_b = 0;

            switch ((int) var_i) {
                case 0:
                    var_r = V;
                    var_g = var_3;
                    var_b = var_1;
                    break;
                case 1:
                    var_r = var_2;
                    var_g = V;
                    var_b = var_1;
                    break;
                case 2:
                    var_r = var_1;
                    var_g = V;
                    var_b = var_3;
                    break;
                case 3:
                    var_r = var_1;
                    var_g = var_2;
                    var_b = V;
                    break;
                case 4:
                    var_r = var_3;
                    var_g = var_1;
                    var_b = V;
                    break;
                default:
                    var_r = V;
                    var_g = var_1;
                    var_b = var_2;
                    break;
            }

            R = var_r * 255;
            G = var_g * 255;
            B = var_b * 255;
        }
        int[] rgbFinal = {(int) R, (int) G, (int) B};
        return rgbFinal;

    }
}
