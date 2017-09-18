package be.ing.fundtransfer.exception;

public class DataInsertionException extends   Exception{
    public DataInsertionException(String msg)
    {
        // Call constructor of parent Exception
        super(msg);
    }
}