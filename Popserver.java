import java.io.*;
import java.util.*;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
public class Popserver{
  static ServerSocket sersock;
  static Socket sock;
  static InputStream istream;
  static OutputStream ostream;
  static PrintWriter pwrite;
  static FileReader reader;
  static BufferedReader br;
  public static void read()
  {
    try{
      String message=br.readLine();
      System.out.println("reading the message "+message);
      String mess="INBOX/"+message;
      reader=new FileReader(mess);
      Properties prop=new Properties();
      prop.load(reader);
      pwrite.println(prop.getProperty("subject"));
      pwrite.println(prop.getProperty("from"));
      pwrite.println(prop.getProperty("date"));
      pwrite.println(prop.getProperty("message"));
      check();
    }
    catch(Exception e)
    {
      System.out.println("exception in reads");
    }
  }
  public static void deletes()
  {
    try
    {
      String message=br.readLine();
      System.out.println("deleting the message "+message);
      String mess="INBOX/"+message;
      File fi=new File(mess);
    if(fi.delete())
    {
      System.out.println(mess+" is deleted");
    }
    else
    {
      System.out.println("dont know why its not deleted");
    }
      check();

    }
    catch(Exception e)
    {
      System.out.println("deletes");
    }
  }
  public static void check()
  {
    try
    {
      String check=br.readLine();
      if(check.equals("read"))
      {
        read();
      }
      else
      {
        deletes();
      }
    }
    catch(Exception e)
    {
      System.out.println("inside catch");
    }
  }
  public static void main(String[] args)throws Exception
 {
   String email="1ks16cs065";
   String password="1ks16cs065";
   sersock=new ServerSocket(4000);
   System.out.println("server ready for connection");
   sock=sersock.accept();
   System.out.println("connection succesful waitimg for the email and password");
   istream =sock.getInputStream();
   br=new BufferedReader(new InputStreamReader(istream));
   String e=br.readLine();
   String p=br.readLine();
   ostream=sock.getOutputStream();
   pwrite=new PrintWriter(ostream,true);
   if(e.equals(email) && p.equals(password))
   {
     pwrite.println("ACCESS GRANTED");
     File file=new File("INBOX");
     String s[]=file.list();
     int l=s.length;
     pwrite.println(l);
     for(String fr :s)
     {
       pwrite.println(fr);
       String fin="INBOX/"+fr;
       File con=new File(fin);
       double z=con.length();
       pwrite.println(z);
     }
     pwrite.println("*");
     check();

   }
   else
   {
     pwrite.println("ACCESS DENIED");
   }
   sock.close();
   sersock.close();
   pwrite.close();
   br.close();
 }
}
