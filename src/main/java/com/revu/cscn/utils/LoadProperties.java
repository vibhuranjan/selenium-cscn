package com.revu.cscn.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadProperties {
	
	static final Logger logger = Logger.getLogger(LoadProperties.class);
	
	private LoadProperties(){
	}

	public static Properties getProperties() throws IOException{
		File configFile = new File("cscn.properties");
		Properties props = null;
		FileReader reader = null;
		try {
		    reader = new FileReader(configFile);
		    props = new Properties();
		    props.load(reader);
		} catch (FileNotFoundException ex) {
			logger.error(ex);
		} catch (IOException ex) {
			logger.error(ex);
		} finally{
			if(reader != null){
				reader.close();
			}
		}
		return props;
	}
}
