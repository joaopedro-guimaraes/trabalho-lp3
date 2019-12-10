/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Thon
 */
public class Criptografia {
    
    public String criptografa(String str) throws NullPointerException, NoSuchAlgorithmException{
        
        MessageDigest algoritimo = MessageDigest.getInstance("MD5");
        algoritimo.update(str.getBytes(), 0, str.length());
        BigInteger numb = new BigInteger(1, algoritimo.digest());        
        String result = String.format("%1$032X", numb);
        return result;
    }
}
