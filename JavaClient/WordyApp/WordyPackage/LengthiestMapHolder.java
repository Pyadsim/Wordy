package WordyApp.WordyPackage;

/**
* WordyApp/WordyPackage/LengthiestMapHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Wordy.idl
* Friday, June 23, 2023 10:47:18 AM CST
*/

public final class LengthiestMapHolder implements org.omg.CORBA.portable.Streamable
{
  public WordyApp.WordyPackage.LengthiestMap value = null;

  public LengthiestMapHolder ()
  {
  }

  public LengthiestMapHolder (WordyApp.WordyPackage.LengthiestMap initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = WordyApp.WordyPackage.LengthiestMapHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    WordyApp.WordyPackage.LengthiestMapHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return WordyApp.WordyPackage.LengthiestMapHelper.type ();
  }

}
