package WordyApp.WordyPackage;

/**
* WordyApp/WordyPackage/WrongPasswordOrUsernameExceptionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Wordy.idl
* Friday, June 23, 2023 10:47:18 AM CST
*/

public final class WrongPasswordOrUsernameExceptionHolder implements org.omg.CORBA.portable.Streamable
{
  public WordyApp.WordyPackage.WrongPasswordOrUsernameException value = null;

  public WrongPasswordOrUsernameExceptionHolder ()
  {
  }

  public WrongPasswordOrUsernameExceptionHolder (WordyApp.WordyPackage.WrongPasswordOrUsernameException initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = WordyApp.WordyPackage.WrongPasswordOrUsernameExceptionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    WordyApp.WordyPackage.WrongPasswordOrUsernameExceptionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return WordyApp.WordyPackage.WrongPasswordOrUsernameExceptionHelper.type ();
  }

}