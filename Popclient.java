import java.net.*;
import java.io.*;
public class Popclient
{
  static  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
  static  OutputStream ostream;
  static  PrintWriter pwrite;
  static  InputStream istream;
  static  BufferedReader socketRead;
  static  String s[]=new String[50];
  static  double d[]=new double[50];
  static int lzz=0;
  public static  void read()
    {
try{
    pwrite.println("read");
    System.out.println("enter the serial number of message to be  read");
    int number=Integer.parseInt(br.readLine());
    String x=s[number-1];
    System.out.println("==============================================");
    System.out.println("showing message for file "+x);
    System.out.println("==============================================");

    pwrite.println(x);
    System.out.println("Subject: "+socketRead.readLine());
    System.out.println("from: "+socketRead.readLine());
    System.out.println("date: "+socketRead.readLine());
    System.out.println("message: "+socketRead.readLine());
    disp();
    choices();
  }
  catch(Exception e)
  {
    System.out.println("exception in readc");
  }
  }
  public static void delete()
  {
    try
    {
      pwrite.println("delete");
      System.out.println("press 1 for deleting first mail \npress 99 for deleting last mail");
      int number=Integer.parseInt(br.readLine());
      if(number==1)
      {
        String x=s[number-1];
        s[number-1]=" ";
        pwrite.println(x);
      }
      else if(number==99)
      {
        String x=s[lzz-1];
        s[lzz-1]=" ";
        pwrite.println(x);
        lzz--;
      }
      else
      {
        System.out.println("INVALID INPUT");
      }

      disp();
      choices();
    }
    catch(Exception e)
    {
      System.out.println("deletec");
    }

  }
  public static void choices()
  {
    int choice=0;
    System.out.println("PRESS 1 : FOR READING \nPRESS 2 : FOR DELETION \nPRESS 3 : FOR EXIT");
    try{
         choice=Integer.parseInt(br.readLine());
       }
       catch(Exception e)
       {
         System.out.println("exception in choicec");
       }
    if(choice==1)
    {
      read();
    }
    if(choice==2)
    {
      delete();
    }
    else if(choice==3)
    {
      System.exit(0);
    }
    else
    {
      System.out.println("INVALID CHOICE");
    }
  }

  public static void disp()
  {

    System.out.println("SNO\t"+"MAIL_NAME"+"\t\t\t"+"MAIL_SIZE");
    for(int i=0;i<lzz;i++)
    {
      if(s[i]!=" ")
      {
        System.out.println((i+1)+"\t"+s[i]+"\t"+d[i]+"bytes");

      }
    }
  }

  public static void main(String[] args)throws IOException
 {
    Socket sock=new Socket(args[2],4000);
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      ostream=sock.getOutputStream();
      pwrite=new PrintWriter(ostream,true);
      pwrite.println(args[0]);
      pwrite.println(args[1]);
      istream=sock.getInputStream();
      socketRead=new BufferedReader(new InputStreamReader(istream));
      String str=socketRead.readLine();
      if(str.equals("ACCESS GRANTED"))
      {
        System.out.println("you are having "+socketRead.readLine()+" messages");
        System.out.println("message name are ->");
        System.out.println("SNO\t"+"MAIL_NAME"+"\t\t"+"MAIL_SIZE");
        while((str=socketRead.readLine())!=null)
        {
          s[lzz]=str;
          if(str.equals("*"))
          {
            break;
          }
          d[lzz]=Double.parseDouble(socketRead.readLine());
          System.out.println((lzz+1)+"\t"+str+"\t"+d[lzz]+"bytes");
          lzz++;
        }
      choices();
    }
    else
    {
      System.out.println("TERMINATED");
      System.exit(0);
    }

      pwrite.close();
      socketRead.close();
      br.close();
      sock.close();

  }

}
