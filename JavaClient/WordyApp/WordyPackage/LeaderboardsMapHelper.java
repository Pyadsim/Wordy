package WordyApp.WordyPackage;


/**
* WordyApp/WordyPackage/LeaderboardsMapHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Wordy.idl
* Friday, June 23, 2023 10:47:18 AM CST
*/

abstract public class LeaderboardsMapHelper
{
  private static String  _id = "IDL:WordyApp/Wordy/LeaderboardsMap:1.0";

  public static void insert (org.omg.CORBA.Any a, WordyApp.WordyPackage.LeaderboardsMap that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static WordyApp.WordyPackage.LeaderboardsMap extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "username",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "wins",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (id(), "LeaderboardsMap", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static WordyApp.WordyPackage.LeaderboardsMap read (org.omg.CORBA.portable.InputStream istream)
  {
    WordyApp.WordyPackage.LeaderboardsMap value = new WordyApp.WordyPackage.LeaderboardsMap ();
    value.username = istream.read_string ();
    value.wins = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, WordyApp.WordyPackage.LeaderboardsMap value)
  {
    ostream.write_string (value.username);
    ostream.write_long (value.wins);
  }

}