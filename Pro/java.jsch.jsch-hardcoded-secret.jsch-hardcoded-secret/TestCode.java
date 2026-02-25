import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class Sftp {
	
	
	public static void f() {

        String password = "aaaa";

        JSch jsch = new JSch();
        session = jsch.getSession("ubuntu", "192.69.69.69",22);
        // ruleid: jsch-hardcoded-secret
        session.setPassword("aaaaa");
        // ok: jsch-hardcoded-secret
        session.setPassword(config);
        // ruleid: jsch-hardcoded-secret
        session.setPassword(password);
        session.connect();
        
    }

}