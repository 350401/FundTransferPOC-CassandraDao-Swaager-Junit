package be.ing.fundtransfer.service;

public interface SendMessageService {
    /**
     * This method is used to generate OTP for sender and Receiver
     */
    public int generateOTP();
    public String sendOTP(String message,String mobile,int Otp);
    }

