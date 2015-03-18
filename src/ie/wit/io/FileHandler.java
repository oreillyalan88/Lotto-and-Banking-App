package ie.wit.io;

import ie.wit.customer.Customer;
import ie.wit.bankaccount.Bank;
import ie.wit.bankaccount.BankRegisterForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;


public class FileHandler <T> { // T allows any class type, any interface type, any array type, or even another type variable to be used in conjunction with these methods, 
								// as long as they/it is specified in the creation of the class

	
	
	public void writeOut(T p, File selection)
			throws FileNotFoundException, IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream( // "try with resources", closes objects that implement
															// java.io.Closeable
				new FileOutputStream(selection))) {
			oos.writeObject(p);
			
		}
	}

	@SuppressWarnings({ "unchecked" })
	public T readIn(File selection) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		T temp = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				selection))){
		
		temp = (T) ois.readObject();
		
			return temp;
		
	}

}
	

	
}
