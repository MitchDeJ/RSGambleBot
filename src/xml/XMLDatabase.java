package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import main.BotUser;
import main.Config;


public class XMLDatabase {
	
	public static boolean userExists(String name) {
		 name = name.replaceAll("#", "-");
		 File file = new File(Config.PATH + Config.USER_FOLDER + name + ".xml");
		 return file.exists();		
	}
	
	public static void saveXML(BotUser user) {
	    File file = new File(Config.PATH + Config.USER_FOLDER + user.getName().replaceAll("#", "-") + ".xml");
	    JAXBContext jaxbContext;
	   if (!file.exists()) {
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   }
		try {
			jaxbContext = JAXBContext.newInstance(BotUser.class);

	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(user, file);
	    jaxbMarshaller.marshal(user, System.out);
	    
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static BotUser LoadUserXML(String filename) {
    	filename = filename.replaceAll("#", "-");
        File file = new File(Config.PATH + Config.USER_FOLDER + filename + ".xml");
        JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(BotUser.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        return (BotUser) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
}
