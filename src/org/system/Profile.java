package org.system;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import gui.tools.X10Apps;

public class Profile {
	
	public static void save(X10Apps apps) {
		try {
			File ftp = new File("./custom/clean/"+Devices.getCurrent().getId()+File.separator+apps.getCurrentProfile()+".ftp");
			byte buffer[] = new byte[10240];
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("Manifest-Version: 1.0\n");
			sbuf.append("Created-By: FlashTool\n");
			sbuf.append("profileName: "+apps.getCurrentProfile()+"\n");
			Manifest manifest = new Manifest(new ByteArrayInputStream(sbuf.toString().getBytes("UTF-8")));
		    FileOutputStream stream = new FileOutputStream(ftp);
		    JarOutputStream out = new JarOutputStream(stream, manifest);
		    out.setLevel(JarOutputStream.STORED);
		    JarEntry jarAdd = new JarEntry("safelist"+apps.getCurrentProfile()+".properties");
	        out.putNextEntry(jarAdd);
	        InputStream in = new FileInputStream(new File("./custom/clean/"+Devices.getCurrent().getId()+File.separator+"safelist"+apps.getCurrentProfile()+".properties"));
	        while (true) {
	          int nRead = in.read(buffer, 0, buffer.length);
	          if (nRead <= 0)
	            break;
	          out.write(buffer, 0, nRead);
	        }
	        in.close();
		    jarAdd = new JarEntry("customlist.properties");
	        out.putNextEntry(jarAdd);
	        in = new FileInputStream(new File("./custom/clean/"+Devices.getCurrent().getId()+File.separator+"customlist.properties"));
	        while (true) {
	          int nRead = in.read(buffer, 0, buffer.length);
	          if (nRead <= 0)
	            break;
	          out.write(buffer, 0, nRead);
	        }
	        in.close();
	        Iterator<String> i = apps.getCurrent().iterator();
	        while (i.hasNext()) {
	        	String key = i.next();
	        	if (apps.customList().containsKey(key)) {
	        		jarAdd = new JarEntry(key);
	        		out.putNextEntry(jarAdd);
	    	        in = new FileInputStream(new File("./custom/apps_saved/"+Devices.getCurrent().getId()+File.separator+key));
	    	        while (true) {
	    	          int nRead = in.read(buffer, 0, buffer.length);
	    	          if (nRead <= 0)
	    	            break;
	    	          out.write(buffer, 0, nRead);
	    	        }
	    	        in.close();
	        	}
	        }
			out.close();
		    stream.close();
		}
		catch (Exception e) {			
		}
	}

}
