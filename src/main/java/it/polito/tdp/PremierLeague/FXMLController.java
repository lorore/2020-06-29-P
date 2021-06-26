/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Month> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<?> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<?> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	this.txtResult.clear();
    	List<Adiacenza> result=this.model.getMigliore();
    	for(Adiacenza a: result) {
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String minimo=this.txtMinuti.getText().trim();
    	if(minimo.isEmpty()) {
    		this.txtResult.setText("minuti non inseriti!");
    		return;
    	}
    	Double min=0.0;
    	try {
    		min=Double.parseDouble(minimo);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Quello inserito non è un numero!");
    		return;
    	}
    	
    	if(min<0.0) {
    		this.txtResult.setText("Inserito un numero negativo!");
    		return;
    	}
    	
    	Month m=this.cmbMese.getValue();
    	if(m==null) {
    		this.txtResult.appendText("Nessun mese inserito!");
    		return;
    	}
    String result=	this.model.creaGrafo(m, min);
    this.txtResult.appendText(result);
    this.btnCollegamento.setDisable(false);
    this.btnConnessioneMassima.setDisable(false);
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Month> mese=new LinkedList<>();;
    	mese.add(Month.JANUARY);
    	mese.add(Month.FEBRUARY);
    	mese.add(Month.MARCH);
    	mese.add(Month.APRIL);
    	mese.add(Month.MAY);
    	mese.add(Month.JUNE);
    	mese.add(Month.JULY);
    	mese.add(Month.AUGUST);
    	mese.add(Month.SEPTEMBER);
    	mese.add(Month.OCTOBER);
    	mese.add(Month.NOVEMBER);
    	mese.add(Month.DECEMBER);
    	this.cmbMese.getItems().addAll(mese);
    	this.btnConnessioneMassima.setDisable(true);
    	this.btnCollegamento.setDisable(true);
  
    }
    
    
}
