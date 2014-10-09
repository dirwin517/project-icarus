package com.icarus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class SSH_Client implements UserInfo, UIKeyboardInteractive{

	final String passwd;
	final String host;
	final String user;
	Session session;

	public SSH_Client(String user, String password, String host) {
		this.user = user;
		this.passwd = password;
		this.host = host;
	}


	public Session connect() throws JSchException{
		JSch jsch=new JSch();  

	session=jsch.getSession(user, host, 22);

		// username and password will be given via UserInfo interface.
		UserInfo ui=new MyUserInfo2();
		session.setUserInfo(ui);
		session.connect();

		return session;			
	}

	public void close(){
		session.disconnect();
	}

	public void doCommand(String command) throws JSchException, IOException {
		Channel channel=session.openChannel("exec");
		((ChannelExec)channel).setCommand(command);

		channel.setInputStream(null);

		((ChannelExec)channel).setErrStream(System.err);

		InputStream in=channel.getInputStream();

		channel.connect();

		byte[] tmp=new byte[1024];
		while(true){
			while(in.available()>0){
				int i=in.read(tmp, 0, 1024);
				if(i<0)break;
				System.out.print(new String(tmp, 0, i));
			}
			if(channel.isClosed()){
				if(in.available()>0) continue; 
				System.out.println("exit-status: "+channel.getExitStatus());
				break;
			}
			try{Thread.sleep(1000);}catch(Exception ee){}
		}
		channel.disconnect();
	}


	public String getPassword(){ return passwd; }
	public boolean promptYesNo(String str){
		return true;
	}

	public String getPassphrase(){ return null; }
	public boolean promptPassphrase(String message){ return true; }
	public boolean promptPassword(String message){return true;}

	public void showMessage(String message){System.out.println(message);}

	public String[] promptKeyboardInteractive(String destination,
			String name,
			String instruction,
			String[] prompt,
			boolean[] echo){

		String result[] = new String[1];
		result[0]=passwd;
		return result;

	}

	
	public static void main(String args[]){
		String user = "daniel.irwin";
		String pass = "iLOVEpickles2332";
		String host = "localhost";
		SSH_Client client = new SSH_Client(user, pass, host);
		try {
			client.connect();
			client.doCommand("touch AWS3");
			client.doCommand("cat AWS3");
			
			client.fileTransferRemote2Local("test.txt", "AWS3");
			client.doCommand("cat AWS3");
		}
		catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.close();
	}
	
	public void fileTransferRemote2Local(final String lfile, final String rfile){
		FileInputStream fis=null;
		try{

			// exec 'scp -t rfile' remotely
			String command="scp " +" -t "+rfile;
			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out=channel.getOutputStream();
			InputStream in=channel.getInputStream();

			channel.connect();

			if(checkAck(in)!=0){
				System.exit(0);
			}

			File _lfile = new File(lfile);

			// send "C0644 filesize filename", where filename should not include '/'
			long filesize=_lfile.length();
			command="C0644 "+filesize+" ";
			if(lfile.lastIndexOf('/')>0){
				command+=lfile.substring(lfile.lastIndexOf('/')+1);
			}
			else{
				command+=lfile;
			}
			command+="\n";
			out.write(command.getBytes()); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}

			// send a content of lfile
			fis=new FileInputStream(lfile);
			byte[] buf=new byte[1024];
			while(true){
				int len=fis.read(buf, 0, buf.length);
				if(len<=0) break;
				out.write(buf, 0, len); //out.flush();
			}
			fis.close();
			fis=null;
			// send '\0'
			buf[0]=0; out.write(buf, 0, 1); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}
			out.close();

			channel.disconnect();

			System.exit(0);
		}
		catch(Exception e){
			System.out.println(e);
			try{if(fis!=null)fis.close();}catch(Exception ee){}
		}
	}

	static int checkAck(InputStream in) throws IOException{
		int b=in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if(b==0) return b;
		if(b==-1) return b;

		if(b==1 || b==2){
			StringBuffer sb=new StringBuffer();
			int c;
			do {
				c=in.read();
				sb.append((char)c);
			}
			while(c!='\n');
			if(b==1){ // error
				System.out.print(sb.toString());
			}
			if(b==2){ // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}
	public static class MyUserInfo2 implements UserInfo, UIKeyboardInteractive{
		String passwd = "iLOVEpickles2332";
		public String getPassword(){ return passwd; }
		public boolean promptYesNo(String str){
			return true;
		}

		public String getPassphrase(){ return null; }
		public boolean promptPassphrase(String message){ return true; }
		public boolean promptPassword(String message){return true;}

		public void showMessage(String message){System.out.println(message);}

		public String[] promptKeyboardInteractive(String destination,
				String name,
				String instruction,
				String[] prompt,
				boolean[] echo){

			String result[] = new String[1];
			result[0]=passwd;
			return result;

		}
	}

}

