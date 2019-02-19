package com.revu.cscn.testing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.revu.cscn.constant.GlobalConstants;
import com.revu.cscn.utils.CommonUtil;
import com.revu.cscn.utils.LoadProperties;

public class Design {

	static final Logger logger = Logger.getLogger(Design.class);
	
	private JFrame frame;
	private JLabel lblNewLabel;
	String filePath = "";
	List<String> listOfFileNames = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Design window = new Design();
					window.frame.setVisible(true);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Design() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnvironment = new JLabel("Environment");
		lblEnvironment.setBounds(23, 29, 104, 14);
		frame.getContentPane().add(lblEnvironment);
		
		final JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"NA", "INT", "QA", "PP", "P", "ALL"}));
		comboBox.setBounds(148, 26, 89, 20);
		frame.getContentPane().add(comboBox);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.INT){
						filePath = LoadProperties.getProperties().getProperty(GlobalConstants.INT);
					} else if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.QA){
						filePath = LoadProperties.getProperties().getProperty(GlobalConstants.QA);
					} else if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.PP){
						filePath = LoadProperties.getProperties().getProperty(GlobalConstants.PP);
					} else if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.P){
						filePath = LoadProperties.getProperties().getProperty(GlobalConstants.P);
					} else if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.ALL){
						listOfFileNames = new ArrayList<String>();
						listOfFileNames.add(LoadProperties.getProperties().getProperty(GlobalConstants.INT));
						listOfFileNames.add(LoadProperties.getProperties().getProperty(GlobalConstants.QA));
						listOfFileNames.add(LoadProperties.getProperties().getProperty(GlobalConstants.PP));
						listOfFileNames.add(LoadProperties.getProperties().getProperty(GlobalConstants.P));
					}
					if(comboBox.getModel().getSelectedItem().toString() == GlobalConstants.ALL){
						for(String fileNamePath : listOfFileNames){
							processSingleFile(fileNamePath);
						}
					} else{
						processSingleFile(filePath);
					}
					lblNewLabel.setText("Processing done!");
				} catch (IOException ex) {
					logger.error(ex);
				}
			}
		});
		btnSubmit.setBounds(148, 74, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(148, 123, 177, 14);
		frame.getContentPane().add(lblNewLabel);
	}
	
	/**
	 * @param filePath
	 * @throws IOException
	 */
	private void processSingleFile(String filePath) throws IOException{
		List<String> listOfUrls = CommonUtil.readDataFromFile(filePath);
		List<String> listOfTitles = MainClass.checkVanityUrl(listOfUrls);
		for(int i=0;i<listOfTitles.size();i++){
			String dataToWrite = "[Status] "+listOfUrls.get(i)+"  ::"+listOfTitles.get(i);
			CommonUtil.writeToFile(LoadProperties.getProperties().getProperty("output.file"), dataToWrite);
		}
	}
}
