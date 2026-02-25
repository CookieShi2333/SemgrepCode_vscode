package example;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;

class BadKryoDeserializationController {
  public doPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    String objectBin = req.getParameter("bin");
    Kryo kryo = new Kryo();
    kryo.setRegistrationRequired(false);

    Input input = new Input(new ByteArrayInputStream(objectBin.getBytes()));
    // ruleid: kryo-deserialization-deepsemgrep
    SomeClass object2 = kryo.readObject(input, SomeClass.class);
    input.close();
  }

  public doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    String objectBin = req.getParameter("bin");
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream(objectBin.getBytes()));
    // ruleid: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
  }

  public okPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream("hardcoded value"));
    // ok: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
  }

  public okPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
    String objectBin = req.getParameter("bin");
    Kryo kryo = new Kryo();
    kryo.register(SomeClass.class);

    Input input = new Input(new ByteArrayInputStream(objectBin.getBytes()));
    // ok: kryo-deserialization-deepsemgrep
    SomeClass object2 = kryo.readObject(input, SomeClass.class);
    input.close();
  }

  public notARequest(String objectBin) throws IOException  {
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream(objectBin));
    // ok: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
  }

}
